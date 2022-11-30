package com.intelizign.tools.tcxmlcomparator.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.intelizign.tools.tcxmlcomparator.infra.Config;
import com.intelizign.tools.tcxmlcomparator.lib.GeneralLib;

public class TcxmlType {
  private String name;
  private List<String> header;
  /**
   * headerIndex uses hash for performance gain. The result is the position of header in header list
   * (and in data).
   */
  private Map<String, Integer> headerIndex;
  /**
   * data has puid as index. puid column is not on data array. Data position must be in sync with
   * header.
   */
  private Map<String, String[]> data;

  /**
   * Header used on the extraction, after the header comparison.
   */
  private List<Header> finalHeader;

  public TcxmlType(String typeName) {
    this.name = typeName;
    this.headerIndex = new HashMap<>();
    this.data = new HashMap<>();
    this.header = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  private static final char PLIC = '"';
  private static final char SEPARATOR = ',';

  public String getStringHeader() {
    StringBuilder str = new StringBuilder();
    str.append(PLIC).append(PUID_ATTR).append(PLIC);
    for (Header header : finalHeader) {
      str.append(SEPARATOR);
      str.append(PLIC).append(header.header).append(PLIC);
    }

    return str.toString();
  }

  public String getStringData(String puid) {
    StringBuilder str = new StringBuilder();

    String[] vData = data.get(puid);
    if (vData != null) {
      str.append(PLIC).append(puid).append(PLIC);
      for (Header header : finalHeader) {
        str.append(SEPARATOR);
        if (header.position < vData.length)
          str.append(PLIC).append(vData[header.position]).append(PLIC);
      }
    }

    return str.toString();
  }

  public Long getSize() {
    return (long) data.size();
  }

  /**
   * Gets an ordered set of puids
   * 
   * @return
   */
  public Set<String> getPuids() {
    return data.keySet().stream().sorted().collect(Collectors.toCollection(LinkedHashSet<String>::new));
  }

  private String tmpPuid;
  private List<String> tmpHeader = new ArrayList<>();
  private List<String> tmpValues = new ArrayList<>();
  private static final String PUID_ATTR = "puid";

  public void addTempValue(String attributeName, String attributeValue) throws TcxmlInvalidFileException {
    try {
      if (!Config.getInstance().ignoreField(attributeName)) {
        if (PUID_ATTR.equals(attributeName)) {
          tmpPuid = attributeValue;
        } else {
          tmpHeader.add(attributeName);
          tmpValues.add(attributeValue);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new TcxmlInvalidFileException(e);
    }
  }

  public Difference getDiff(String puid, TcxmlType type2) {
    String[] data1 = data.get(puid);
    String[] data2 = type2.data.get(puid);
    Difference result = new Difference();

    for (String header : headerIndex.keySet()) {
      Integer id1 = headerIndex.get(header);
      Integer id2 = type2.headerIndex.get(header);
      if (data1.length > id1 && id2 == null) {
        result.headerOrphansA.add(header);
      } else if (id2 != null && data2.length > id2 && data1.length <= id1) {
        result.headerOrphansB.add(header);
      } else if (id2 != null && data1.length > id1 && data2.length > id1) {
        if (!GeneralLib.stringCompare(data1[id1], data2[id2])) {
          result.hasDifference = true;
          result.addDetails(header, data1[id1], data2[id2]);
        }
      }
    }

    for (String header : type2.headerIndex.keySet()) {
      Integer id1 = headerIndex.get(header);
      if (id1 == null) {
        result.headerOrphansB.add(header);
      }
    }
    return result;
  }

  public class Difference {
    List<String> headerOrphansA = new ArrayList<>();
    List<String> headerOrphansB = new ArrayList<>();
    boolean hasDifference = false;
    List<Details> details = new ArrayList<>();

    public List<String> getHeaderOrphansA() {
      return headerOrphansA;
    }

    public void addDetails(String header, String value1, String value2) {
      details.add(new Details(header, value1, value2));
    }

    public void setHeaderOrphansA(List<String> headerOrphansA) {
      this.headerOrphansA = headerOrphansA;
    }

    public List<String> getHeaderOrphansB() {
      return headerOrphansB;
    }

    public void setHeaderOrphansB(List<String> headerOrphansB) {
      this.headerOrphansB = headerOrphansB;
    }

    public boolean isHasDifference() {
      return hasDifference;
    }

    public void setHasDifference(boolean hasDifference) {
      this.hasDifference = hasDifference;
    }

    public List<Details> getDetails() {
      return details;
    }

    public class Details {
      String header;
      String value1;
      String value2;

      public Details(String header, String value1, String value2) {
        this.header = header;
        this.value1 = value1;
        this.value2 = value2;
      }

      public String getHeader() {
        return header;
      }

      public void setHeader(String header) {
        this.header = header;
      }

      public String getValue1() {
        return value1;
      }

      public void setValue1(String value1) {
        this.value1 = value1;
      }

      public String getValue2() {
        return value2;
      }

      public void setValue2(String value2) {
        this.value2 = value2;
      }
    }
  }

  /**
   * Persists into memory the temp values
   */
  public void commit() {
    // Checks if we have new header
    for (String str : tmpHeader) {
      checkHeader(str);
    }
    // Creates a vector with the size of the header
    String[] row = new String[header.size()];
    data.put(tmpPuid, row);

    // Vector must be on sync with header
    for (int i = 0; i < tmpHeader.size(); i++) {
      int id = indexOfHeader(tmpHeader.get(i));
      row[id] = tmpValues.get(i);
    }

    // Clean the temp values
    this.tmpHeader.clear();
    this.tmpValues.clear();
    this.tmpPuid = null;
  }

  private void checkHeader(String head) {
    if (!headerIndex.containsKey(head)) {
      header.add(head);
      headerIndex.put(head, header.size() - 1);
    }
  }

  private int indexOfHeader(String head) {
    Integer result = headerIndex.get(head);
    if (result == null) {
      header.add(head);
      result = header.size() - 1;
      headerIndex.put(head, result);
    }
    return result;
  }

  public void compareHeaders(TcxmlType type2, ReportDTO report) {
    List<Header> header1 = new ArrayList<>();
    List<Header> header2 = new ArrayList<>();
    for (int i = 0; i < header.size(); i++) {
      Integer i2 = type2.headerIndex.get(header.get(i));

      if (i2 != null) {
        header1.add(new Header(header.get(i), i));
        header2.add(new Header(header.get(i), i2));
      } else {
        // add exception to the report
        TcxmlReportType type = report.addType(getName());
        type.addOrphanFieldA(header.get(i));
      }
    }

    // Check for exceptions on the other side
    for (int i = 0; i < type2.header.size(); i++) {
      Integer i1 = headerIndex.get(type2.header.get(i));
      if (i1 == null) {
        // add exception to the report
        TcxmlReportType type = report.addType(getName());
        type.addOrphanFieldB(type2.header.get(i));
      }
    }

    this.finalHeader = header1;
    type2.finalHeader = header2;
  }


  protected class Header {
    public Header(String header, int position) {
      this.header = header;
      this.position = position;
    }

    public String header;
    public int position;
  }
}
