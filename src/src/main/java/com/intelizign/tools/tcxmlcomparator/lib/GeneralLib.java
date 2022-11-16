package com.intelizign.tools.tcxmlcomparator.lib;

public class GeneralLib {
  public static void close(AutoCloseable obj) {
    if (obj != null) {
      try {
        obj.close();
      } catch (Exception e) {
      }
    }
  }
}
