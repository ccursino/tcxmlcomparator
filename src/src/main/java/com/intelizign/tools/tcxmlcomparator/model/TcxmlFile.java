package com.intelizign.tools.tcxmlcomparator.model;

import java.io.File;
import java.util.Map;

public class TcxmlFile {
  private File file;
  private Map<String, TcxmlType> types;

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public Map<String, TcxmlType> getTypes() {
    return types;
  }

  public void setTypes(Map<String, TcxmlType> types) {
    this.types = types;
  }
  
  
  

}
