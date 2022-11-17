package com.intelizign.tools.tcxmlcomparator.infra;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.intelizign.tools.tcxmlcomparator.lib.GeneralLib;
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
      writeFile(tcxml.getType(typeName), csvFile);
    }
  }

  private void writeFile(TcxmlType data, File csvFile) throws IOException {
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
}
