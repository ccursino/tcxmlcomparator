package com.intelizign.tools.tcxmlcomparator.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TcxmlFile {
  private File file;
  private Map<String, TcxmlType> typeMap;

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public Set<String> getTypes() {
    return typeMap.keySet();
  }

  public TcxmlType getType(String typeName) {
    if (typeMap == null) {
      typeMap = new HashMap<>();
    }
    TcxmlType result = typeMap.get(typeName);
    if (result == null) {
      result = new TcxmlType(typeName);
      typeMap.put(typeName, result);
    }
    return result;
  }

  /**
   * Compare the headers of each type, add the differences to the report and creates an equal header
   * to both files. Types that exists only in one file will be considered as differences (will be
   * extracted as is).
   * 
   * @param tcxml2
   * @param report
   */
  public void compareHeadersWith(TcxmlFile tcxml2, ReportDTO report) {
    for (TcxmlType type1 : this.typeMap.values()) {
      TcxmlType type2 = tcxml2.getType(type1.getName());
      if (type2 != null) {
        type1.compareHeaders(type2, report);
      } 
    }
  }
}
