package com.intelizign.tools.tcxmlcomparator.model;

public class TcxmlReportField {
  private String name;
  private Long qt = 0L;

  public TcxmlReportField(String name) {
    this.name = name;
  }

  private StringBuilder detail = new StringBuilder();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getQt() {
    return qt;
  }

  public void setQt(Long qt) {
    this.qt = qt;
  }

  public void addQt() {
    qt++;
  }

  public String getDetail() {
    return detail.toString();
  }

  public void addDetail(String str) {
    this.detail.append(str).append('\n');
  }

  @Override
  public String toString() {
    return name + ":" + qt + '\n';
  }

}
