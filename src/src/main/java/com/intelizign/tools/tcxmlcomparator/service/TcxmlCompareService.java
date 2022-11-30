package com.intelizign.tools.tcxmlcomparator.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import com.intelizign.tools.tcxmlcomparator.infra.CsvWritter;
import com.intelizign.tools.tcxmlcomparator.infra.TcxmlReader;
import com.intelizign.tools.tcxmlcomparator.model.ReportDTO;
import com.intelizign.tools.tcxmlcomparator.model.TcxmlFile;
import com.intelizign.tools.tcxmlcomparator.model.TcxmlInvalidFileException;
import com.intelizign.tools.tcxmlcomparator.model.TcxmlReportType;
import com.intelizign.tools.tcxmlcomparator.model.TcxmlType;

public class TcxmlCompareService {
  private static TcxmlCompareService instance = new TcxmlCompareService();

  private TcxmlCompareService() {}

  public static TcxmlCompareService getInstance() {
    return instance;
  }

  /**
   * Compare 2 tcxml files, producing a report.txt and a comparing structure.
   * 
   * @param file1
   * @param file2
   * @throws TcxmlInvalidFileException
   */
  public ReportDTO compareTcxmlFiles(File file1, File file2) throws TcxmlInvalidFileException {
    // Create file structure
    File parentFolder = createFolderStructure();

    TcxmlFile tcxml1 = TcxmlReader.getInstance().extractFile(file1);
    TcxmlFile tcxml2 = TcxmlReader.getInstance().extractFile(file2);

    ReportDTO report = new ReportDTO();

    // Compare the headers of both files.
    // The extraction must be made only with common attributes. The other attributes will be on the
    // final report.
    tcxml1.compareHeadersWith(tcxml2, report);

    // Extract the first file
    try {
      CsvWritter.getInstance().extractCsv(parentFolder, tcxml1);

      // Extract the second file
      CsvWritter.getInstance().extractCsv(parentFolder, tcxml2);

      makeReportResume(report, tcxml1, tcxml2);
      CsvWritter.getInstance().extractReport(parentFolder, report);

      return report;

    } catch (IOException e) {
      e.printStackTrace();
      throw new TcxmlInvalidFileException(e);
    }

  }

  private File createFolderStructure() {
    final String DATE_FORMAT = "yyyyMMdd_HHmmssSSS";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    String folderName = "results/" + formatter.format(LocalDateTime.now());

    File folder = new File(folderName);
    folder.mkdirs();
    return folder;
  }

  public void makeReportResume(ReportDTO report, TcxmlFile file1, TcxmlFile file2) {
    // Search for orphans in file1, and get all the differences on common types and puids, and puid
    // orphans in file1
    for (String typeName : file1.getTypes()) {
      TcxmlReportType reportType = report.addType(typeName);

      TcxmlType type1 = file1.getType(typeName);
      reportType.setQtPuidA(type1.getSize());

      TcxmlType type2 = file2.getType(typeName);
      if (type2 == null) {
        reportType.setQtOrphanPuidA(type1.getSize());
      } else {
        Set<String> setPuids2 = type2.getPuids();

        for (String puid : type1.getPuids()) {
          if (setPuids2.contains(puid)) {
            // Make the diff
            // Compare each field, computing also the field orphans
            TcxmlType.Difference diff = type1.getDiff(puid, type2);
            if (diff.isHasDifference()) {
              reportType.addDiff();
              for (TcxmlType.Difference.Details detail : diff.getDetails()) {
                reportType.addFieldDiff(puid, detail.getHeader(), detail.getValue1(), detail.getValue2());
              }
            }
          } else {
            reportType.addPuidOrphanA();
          }
        }
      }

    }

    // Search for orphans in file2, and get the puid orphans in file2
    for (String typeName : file2.getTypes()) {
      TcxmlReportType reportType = report.addType(typeName);
      TcxmlType type1 = file1.getType(typeName);
      TcxmlType type2 = file2.getType(typeName);
      if (type1 == null) {
        reportType.setQtOrphanPuidB(type2.getSize());
      } else {
        reportType.setQtPuidB(type2.getSize());

        Set<String> setPuids1 = type1.getPuids();

        for (String puid : type2.getPuids()) {
          if (!setPuids1.contains(puid)) {
            reportType.addPuidOrphanB();
          }
        }
      }
    }
  }
}
