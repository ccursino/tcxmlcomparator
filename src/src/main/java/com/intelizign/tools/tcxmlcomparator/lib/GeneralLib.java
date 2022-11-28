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

  public static boolean stringCompare(String str1, String str2) {
    if (str1 == null && str2 == null)
      return true;
    if (str1 == null && str2 != null)
      return false;
    return str1.equals(str2);
  }
}
