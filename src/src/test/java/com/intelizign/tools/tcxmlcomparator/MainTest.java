package com.intelizign.tools.tcxmlcomparator;

import java.io.File;
import org.junit.Test;
import com.intelizign.tools.tcxmlcomparator.model.TcxmlInvalidFileException;
import com.intelizign.tools.tcxmlcomparator.service.TcxmlCompareService;

public class MainTest {

  @Test
  public void testCompare() throws TcxmlInvalidFileException {
    File f1 = new File("../samples/sample1.xml");
    File f2 = new File("../samples/sample2.xml");
    // File f1 = new File("../samples/testFile.xml");
    // File f1 = new File("../samples/bigSample.xml");
    // File f2 = new File("../samples/bigSample2.xml");

    TcxmlCompareService.getInstance().compareTcxmlFiles(f1, f2);
  }
}
