package com.intelizign.tools.tcxmlcomparator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TcxmlReportType {
  private String name;
  private Long qtPuidA;
  private Long qtPuidB;
  private Long qtOrphanPuidA = 0L;
  private Long qtOrphanPuidB = 0L;
  private Long diffPuids = 0L;
  private List<TcxmlReportField> fieldDiff = new ArrayList<>();
  private List<TcxmlReportField> orphanFieldA = new ArrayList<>();
  private List<TcxmlReportField> orphanFieldB = new ArrayList<>();

  public TcxmlReportType(String typeName) {
    this.name = typeName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getQtPuidA() {
    return qtPuidA;
  }

  public void setQtPuidA(Long qtPuidA) {
    this.qtPuidA = qtPuidA;
  }

  public Long getQtPuidB() {
    return qtPuidB;
  }

  public void setQtPuidB(Long qtPuidB) {
    this.qtPuidB = qtPuidB;
  }

  public Long getQtOrphanPuidA() {
    return qtOrphanPuidA;
  }

  public void setQtOrphanPuidA(Long qtOrphanPuidA) {
    this.qtOrphanPuidA = qtOrphanPuidA;
  }

  public Long getQtOrphanPuidB() {
    return qtOrphanPuidB;
  }

  public void setQtOrphanPuidB(Long qtOrphanPuidB) {
    this.qtOrphanPuidB = qtOrphanPuidB;
  }

  public Long getDiffPuids() {
    return diffPuids;
  }

  public void addDiff() {
    this.diffPuids++;
  }


  public List<TcxmlReportField> getListFieldDiff() {
    return fieldDiff;
  }

  public TcxmlReportField getFieldDiff(String fieldName) {
    Optional<TcxmlReportField> result =
        fieldDiff.stream().filter(o -> fieldName.equals(o.getName())).findAny();
    if (result.isPresent())
      return result.get();
    return null;
  }

  public void addFieldDiff(String puid, String fieldName, String value1, String value2) {
    TcxmlReportField field = getFieldDiff(fieldName);
    if (field == null) {
      field = new TcxmlReportField(fieldName);
      fieldDiff.add(field);
    }
    field.addQt();
    field.addDetail(puid + " - " + value1 + " : " + value2);
  }

  public List<TcxmlReportField> getOrphanFieldA() {
    return orphanFieldA;
  }

  public List<TcxmlReportField> getOrphanFieldB() {
    return orphanFieldB;
  }

  public void addPuidOrphanA() {
    this.qtOrphanPuidA++;
  }

  public void addPuidOrphanB() {
    this.qtOrphanPuidB++;
  }
  
  public void addOrphanFieldA(String fieldName) {
    orphanFieldA.add(new TcxmlReportField(fieldName));
  }
  
  public void addOrphanFieldB(String fieldName) {
    orphanFieldB.add(new TcxmlReportField(fieldName));
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    char lf = '\n';
    result.append("============================").append(lf);
    result.append("Type: ").append(name).append(lf);
    result.append("============================").append(lf);
    result.append("Puid's in 1st file: ").append(qtPuidA).append(lf);
    result.append("Puid's in 2nd file: ").append(qtPuidB).append(lf);
    result.append("Orphans records in 1st file: ").append(qtOrphanPuidA).append(lf);
    result.append("Orphans records in 2nd file: ").append(qtOrphanPuidB).append(lf);
    if (diffPuids > 0) {
      result.append("Records with differences between 1st and 2nd file: ").append(diffPuids)
          .append(lf);
      result.append(">>>> Differences:").append(lf);
      for (TcxmlReportField tcxmlReportField : fieldDiff) {
        result.append(tcxmlReportField.toString());
      }
      result.append("<<<<");
    } else {
      result.append("No differences found between 1st and 2nd file").append(diffPuids).append(lf);
    }
    if (orphanFieldA.size() > 0) {
      result.append(">>>> Fields in 1st file not found in 2nd file:").append(lf);
      boolean first = true;
      for (TcxmlReportField tcxmlReportField : orphanFieldA) {
        if (!first) {
          result.append(", ");
          first = false;
        }
        result.append(tcxmlReportField.getName());
      }
      result.append(lf).append("<<<<");
    }
    
    if (orphanFieldB.size() > 0) {
      result.append(">>>> Fields in 2nd file not found in 1st file:").append(lf);
      boolean first = true;
      for (TcxmlReportField tcxmlReportField : orphanFieldB) {
        if (!first) {
          result.append(", ");
          first = false;
        }
        result.append(tcxmlReportField.getName());
      }
      result.append(lf).append("<<<<");
    }
    return result.toString();
  }
}
