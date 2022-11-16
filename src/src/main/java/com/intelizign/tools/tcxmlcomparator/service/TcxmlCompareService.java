package com.intelizign.tools.tcxmlcomparator.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.intelizign.tools.tcxmlcomparator.infra.TcxmlReader;
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
    //Get the first file
    TcxmlFile t1 = null;
    
    // Create file structure
    File parentFolder = createFolderStructure();
    
    TcxmlFile tcxml1 = TcxmlReader.getInstance().extractFile(file1);

  }

  private File createFolderStructure() {
    final String DATE_FORMAT = "yyyyMMdd_HHmmssSSS";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    String folderName = "results/" + formatter.format(LocalDateTime.now());

    File folder = new File(folderName);
    folder.mkdirs();
    return folder;
  }
}
