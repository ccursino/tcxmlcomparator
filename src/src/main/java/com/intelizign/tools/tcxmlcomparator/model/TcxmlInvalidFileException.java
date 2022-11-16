package com.intelizign.tools.tcxmlcomparator.model;

public class TcxmlInvalidFileException extends Exception {

  private static final long serialVersionUID = 3072493099595179543L;

  public TcxmlInvalidFileException(String msg) {
    super(msg);
  }

  public TcxmlInvalidFileException(Exception e) {
    super(e);
  }
}
