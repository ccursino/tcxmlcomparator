package com.intelizign.tools.tcxmlcomparator;

import java.io.File;
import com.intelizign.tools.tcxmlcomparator.model.TcxmlInvalidFileException;
import com.intelizign.tools.tcxmlcomparator.service.TcxmlCompareService;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) {
    if (args.length != 2) {
      help();
      return;
    }
    File file1 = new File(args[0]);
    File file2 = new File(args[1]);

    for (File f : new File[] {file1, file2}) {
      if (!f.exists()) {
        System.out.println("File " + f.getAbsolutePath() + " not found");
        help();
        return;
      }
    }
    
    try {
      TcxmlCompareService.getInstance().compareTcxmlFiles(file1, file2);
    } catch (TcxmlInvalidFileException e) {
      System.out.println("Error: " + e.getMessage());
      help();
    }
    
  }

  private static void help() {
    System.out.println("Expected arguments:");
    System.out.println("file1.xml file2.xml");
  }
}
