package com.intelizign.tools.tcxmlcomparator.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.intelizign.tools.tcxmlcomparator.infra.CsvWritter;
import com.intelizign.tools.tcxmlcomparator.infra.TcxmlReader;
import com.intelizign.tools.tcxmlcomparator.model.ReportDTO;
import com.intelizign.tools.tcxmlcomparator.model.TcxmlFile;
import com.intelizign.tools.tcxmlcomparator.model.TcxmlInvalidFileException;

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
  public void compareTcxmlFiles(File file1, File file2) throws TcxmlInvalidFileException {
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

      // Extract the differences and compute it into the report
      // TODO:make the extraction of the differences

      makeReportResume(report, tcxml1, tcxml2);

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
    
  }
}
