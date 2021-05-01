package com.daema.rest.common.exception;

import java.text.ParseException;

public class NonExistDateException extends ParseException {

  public NonExistDateException(String s) {
    this(s, -1);
  }

  public NonExistDateException(String s,int errOffset) {
    super(s, errOffset);
  }
}
