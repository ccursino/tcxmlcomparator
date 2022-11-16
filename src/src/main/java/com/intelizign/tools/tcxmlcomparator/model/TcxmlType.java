package com.intelizign.tools.tcxmlcomparator.model;

import java.util.Map;

public class TcxmlType {
  private String[] header;
  private Map<String, Integer> headerIndex;
  private Map<String, String[]> data;

  public String[] getHeader() {
    return header;
  }

  public void setHeader(String[] header) {
    this.header = header;
  }

  public Map<String, Integer> getHeaderIndex() {
    return headerIndex;
  }

  public void setHeaderIndex(Map<String, Integer> headerIndex) {
    this.headerIndex = headerIndex;
  }

  public Map<String, String[]> getData() {
    return data;
  }

  public void setData(Map<String, String[]> data) {
    this.data = data;
  }

}
