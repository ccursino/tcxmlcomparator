package com.intelizign.tools.tcxmlcomparator.infra;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.intelizign.tools.tcxmlcomparator.lib.GeneralLib;
import com.intelizign.tools.tcxmlcomparator.model.ReportDTO;
import com.intelizign.tools.tcxmlcomparator.model.TcxmlFile;
import com.intelizign.tools.tcxmlcomparator.model.TcxmlType;

public class CsvWritter {
  private static CsvWritter instance = new CsvWritter();

  private CsvWritter() {}

  public static CsvWritter getInstance() {
    return instance;
  }

  public void extractCsv(File rootParentFolder, TcxmlFile tcxml) throws IOException {
    File parentFolder = new File(rootParentFolder, tcxml.getFile().getName());
    parentFolder.mkdirs();

    for (String typeName : tcxml.getTypes()) {
      File csvFile = new File(parentFolder, typeName + ".csv");
      writeCsvFile(tcxml.getType(typeName), csvFile);
    }
  }

  public void extractReport(File rootParentFolder, ReportDTO report) throws IOException {
    File reportFile = new File(rootParentFolder, "report.txt");

    writeDataToFile(report.toString(), reportFile);
  }

  private void writeCsvFile(TcxmlType data, File csvFile) throws IOException {
    char lf = '\n';
    BufferedWriter bw = null;
    FileWriter fw = null;
    try {
      fw = new FileWriter(csvFile);
      bw = new BufferedWriter(fw);
      bw.write(data.getStringHeader() + lf);

      for (String puid : data.getPuids()) {
        bw.write(data.getStringData(puid) + lf);
      }

      bw.flush();

    } finally {
      GeneralLib.close(fw);
      GeneralLib.close(bw);
    }
  }

  private void writeDataToFile(String data, File csvFile) throws IOException {
    BufferedWriter bw = null;
    FileWriter fw = null;
    try {
      fw = new FileWriter(csvFile);
      bw = new BufferedWriter(fw);
      bw.write(data);

      bw.flush();

    } finally {
      GeneralLib.close(fw);
      GeneralLib.close(bw);
    }
  }
}
