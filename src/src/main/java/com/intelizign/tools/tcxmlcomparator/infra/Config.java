package com.intelizign.tools.tcxmlcomparator.infra;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class Config {
  private static Config instance = null;
  private Properties prop;

  private Config() throws IOException {
    this.prop = loadProperties();
  }

  public static Config getInstance() throws IOException {
    if (instance == null)
      instance = new Config();

    return instance;
  }

  private Set<String> fieldsToIgnore = null;

  public String getFieldsToIgnore() {
    return prop.getProperty("fields_to_ignore");
  }

  public boolean ignoreField(String field) {
    if (fieldsToIgnore == null) {
      String str = getFieldsToIgnore();
      if (str == null || "".equals(str)) {
        fieldsToIgnore = new HashSet<>();
        System.out.println("No fields will be ignored");
      } else {
        System.out.println("Fields ignored:" + str);
        fieldsToIgnore = Arrays.asList(str.split(",")).stream().collect(Collectors.toSet());
      }
    }
    return fieldsToIgnore.contains(field);
  }


  public Properties loadProperties() throws IOException {
    Properties configuration = new Properties();
    InputStream inputStream =
        Config.class.getClassLoader().getResourceAsStream("application.properties");
    configuration.load(inputStream);
    inputStream.close();
    return configuration;
  }


}
