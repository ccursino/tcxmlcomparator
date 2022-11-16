package com.intelizign.tools.tcxmlcomparator.infra;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import com.intelizign.tools.tcxmlcomparator.lib.GeneralLib;
import com.intelizign.tools.tcxmlcomparator.lib.VTDGenExtended;
import com.intelizign.tools.tcxmlcomparator.lib.VTDNavExtended;
import com.intelizign.tools.tcxmlcomparator.model.TcxmlFile;
import com.intelizign.tools.tcxmlcomparator.model.TcxmlInvalidFileException;
import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.ParseException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

public class TcxmlReader {
  private static TcxmlReader instance = new TcxmlReader();

  public TcxmlReader() {}

  public static TcxmlReader getInstance() {
    return instance;
  }

  public TcxmlFile extractFile(File file) throws TcxmlInvalidFileException {
    FileInputStream is = null;
    FileInputStream fis = null;
    try {
      is = new FileInputStream(file);
      fis = new FileInputStream(file);
      byte[] b = new byte[(int) file.length()];
      fis.read(b);

      VTDGenExtended vg = new VTDGenExtended();
      vg.setDoc(b);
      vg.parse(false); // set namespace awareness to true

      //VTDNav vn = vg.getNav();
      VTDNavExtended vn = vg.getNavExt();
      AutoPilot ap = new AutoPilot(vn);

      ap.selectXPath("//TCXML/child::*");

      TcxmlFile tResult = new TcxmlFile();
      tResult.setFile(file);

      int result = -1;
      int count = 0;
      while ((result = ap.evalXPath()) != -1) {


        System.out.print("" + result + " ");
        System.out.print("Element name ==> " + vn.toString(result) + vn.getAttrCount());
        
        Map<String, String> mp= vn.getAttrMap();

        // int t = vn.getText(); // get the index of the text (char data or CDATA)
        // if (t != -1)
        // System.out.println(" Text ==> " + vn.toNormalizedString(t));
        System.out.println("\n ============================== ");
        count++;
      }
      System.out.println("Total # of element " + count);

      return null;


    } catch (IOException | XPathParseException | ParseException | XPathEvalException
        | NavException e) {
      e.printStackTrace();
      throw new TcxmlInvalidFileException(e);
    } finally {

      GeneralLib.close(fis);
      GeneralLib.close(is);
    }
  }

}
