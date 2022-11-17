package com.intelizign.tools.tcxmlcomparator.lib;

import com.intelizign.tools.tcxmlcomparator.model.TcxmlType;
import com.ximpleware.FastIntBuffer;
import com.ximpleware.FastLongBuffer;
import com.ximpleware.IByteBuffer;
import com.ximpleware.NavException;
import com.ximpleware.VTDNav_L5;

public class VTDNavExtended extends VTDNav_L5 {

  protected VTDNavExtended(int RootIndex, int enc, boolean NS, int depth, IByteBuffer x,
      FastLongBuffer vtd, FastLongBuffer l1, FastLongBuffer l2, FastLongBuffer l3,
      FastLongBuffer l4, FastIntBuffer l5, int so, int length) {
    super(RootIndex, enc, NS, depth, x, vtd, l1, l2, l3, l4, l5, so, length);
  }

  public void feedData(TcxmlType tcxmlType) throws NavException {
    if (context[0] == -1)
      return;
    // int count = 0;
    int index = getCurrentIndex() + 1;
    while (index < vtdSize) {
      int type = getTokenType(index);
      if (type == TOKEN_ATTR_NAME || type == TOKEN_ATTR_VAL || type == TOKEN_ATTR_NS) {
        if (type == TOKEN_ATTR_NAME || (!ns && (type == TOKEN_ATTR_NS))) {
          tcxmlType.addTempValue(toNormalizedString(index), toNormalizedString(index + 1));
        }
      } else
        break;
      index++;
    }
    tcxmlType.commit();
  }
}
