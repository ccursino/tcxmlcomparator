package com.intelizign.tools.tcxmlcomparator.lib;

import java.util.HashMap;
import java.util.Map;
import com.ximpleware.FastIntBuffer;
import com.ximpleware.FastLongBuffer;
import com.ximpleware.IByteBuffer;
import com.ximpleware.NavException;
import com.ximpleware.UniByteBuffer;
import com.ximpleware.VTDNav_L5;

public class VTDNavExtended extends VTDNav_L5 {

  protected VTDNavExtended(int RootIndex, int enc, boolean NS, int depth, IByteBuffer x,
      FastLongBuffer vtd, FastLongBuffer l1, FastLongBuffer l2, FastLongBuffer l3,
      FastLongBuffer l4, FastIntBuffer l5, int so, int length) {
    super(RootIndex, enc, NS, depth, x, vtd, l1, l2, l3, l4, l5, so, length);
  }

  public Map<String, String> getAttrMap() throws NavException {
    Map<String, String> result = new HashMap<>();

    if (context[0] == -1)
      return result;
    // int count = 0;
    int index = getCurrentIndex() + 1;
    while (index < vtdSize) {
      int type = getTokenType(index);
      if (type == TOKEN_ATTR_NAME || type == TOKEN_ATTR_VAL || type == TOKEN_ATTR_NS) {
        if (type == TOKEN_ATTR_NAME || (!ns && (type == TOKEN_ATTR_NS))) {
          String header = toNormalizedString(index);
          String value = toNormalizedString(index+1);
          
          //result.put(header, value)
          //faca algo mais inteligente, tipo ja alimentar um mapa previo
          
        }
      } else
        break;
      index++;
    }
    return result;
  }
}
