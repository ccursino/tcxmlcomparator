package com.intelizign.tools.tcxmlcomparator.lib;

import com.ximpleware.UniByteBuffer;
import com.ximpleware.VTDGen;

public class VTDGenExtended extends VTDGen {

  public VTDGenExtended() {
    super();
  }

  public VTDNavExtended getNavExt() {
    // call VTDNav constructor
    VTDNavExtended vn;
    vn = new VTDNavExtended(rootIndex, encoding, ns, VTDDepth, new UniByteBuffer(XMLDoc), VTDBuffer,
        l1Buffer, l2Buffer, _l3Buffer, _l4Buffer, _l5Buffer, docOffset, docLen);
    clear();
    // r = new UTF8Reader();
    return vn;
  }

}
