package com.intelizign.tools.tcxmlcomparator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportDTO {
  private List<TcxmlReportType> types = new ArrayList<>();

  public List<TcxmlReportType> getTypes() {
    return types;
  }

  public TcxmlReportType getType(String typeName) {
    Optional<TcxmlReportType> result =
        types.stream().filter(o -> typeName.equals(o.getName())).findAny();

    return result.isPresent() ? result.get() : null;
  }

  /**
   * Adds the type and return the object.
   * 
   * @param typeName
   * @return New type (or existing type if it alread exists)
   */
  public TcxmlReportType addType(String typeName) {
    TcxmlReportType result = getType(typeName);
    if (result == null) {
      result = new TcxmlReportType(typeName);
      types.add(result);
    }

    return result;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (TcxmlReportType tcxmlReportType : types) {
      result.append(tcxmlReportType.toString()).append('\n');
    }
    return result.toString();
  }


}
