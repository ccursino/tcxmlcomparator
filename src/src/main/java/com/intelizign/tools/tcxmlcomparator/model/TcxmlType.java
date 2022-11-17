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

  /**
   * Gets an ordered set of puids
   * 
   * @return
   */
  public Set<String> getPuids() {
    // Set<String> setPuids = new

    return data.keySet().stream().sorted()
        .collect(Collectors.toCollection(LinkedHashSet<String>::new));
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

  /**
   * Persists into memory the temp values
   */
  public void commit() {
    // Creates a vector with the size of temp
    String[] row = new String[tmpHeader.size()];
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

  private int indexOfHeader(String head) {
    Integer result = headerIndex.get(head);
    if (result == null) {
      header.add(head);
      result = header.size() - 1;
      headerIndex.put(head, result);
    }
    return result;
  }

  public void compareHeaders(TcxmlType type2) {
    List<Header> header1 = new ArrayList<>();
    List<Header> header2 = new ArrayList<>();
    for (int i = 0; i < header.size(); i++) {
      Integer i2 = type2.headerIndex.get(header.get(i));

      if (i2 != null) {
        header1.add(new Header(header.get(i), i));
        header2.add(new Header(header.get(i), i2));
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
