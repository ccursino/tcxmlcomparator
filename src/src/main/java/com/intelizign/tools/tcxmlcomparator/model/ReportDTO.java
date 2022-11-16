package com.intelizign.tools.tcxmlcomparator.model;

import java.util.List;

public class ReportDTO {
  private List<TcxmlReportType> types;

  public List<TcxmlReportType> getTypes() {
    return types;
  }

  public void setTypes(List<TcxmlReportType> types) {
    this.types = types;
  }
}
