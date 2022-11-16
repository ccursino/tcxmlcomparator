package com.intelizign.tools.tcxmlcomparator.model;

import java.util.List;

public class TcxmlReportType {
  private String name;
  private Long qtPuid;
  private Long qtOrfanPuid;
  private Long diffPuids;
  private List<TcxmlReportField> fieldDiff;
  private List<TcxmlReportField> orfanField;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getQtPuid() {
    return qtPuid;
  }

  public void setQtPuid(Long qtPuid) {
    this.qtPuid = qtPuid;
  }

  public Long getQtOrfanPuid() {
    return qtOrfanPuid;
  }

  public void setQtOrfanPuid(Long qtOrfanPuid) {
    this.qtOrfanPuid = qtOrfanPuid;
  }

  public Long getDiffPuids() {
    return diffPuids;
  }

  public void setDiffPuids(Long diffPuids) {
    this.diffPuids = diffPuids;
  }

  public List<TcxmlReportField> getFieldDiff() {
    return fieldDiff;
  }

  public void setFieldDiff(List<TcxmlReportField> fieldDiff) {
    this.fieldDiff = fieldDiff;
  }

  public List<TcxmlReportField> getOrfanField() {
    return orfanField;
  }

  public void setOrfanField(List<TcxmlReportField> orfanField) {
    this.orfanField = orfanField;
  }


}
