package com.demo.sso.domain.user.exception;

import com.demo.sso.global.exception.DataNotFoundException;

public class PhoneNotFoundException extends DataNotFoundException {
  private static final String DEFAULT_MESSAGE = "해당 핸드폰이 없습니다.";

  private PhoneNotFoundException(String message) {
    super(message);
  }


  public static PhoneNotFoundException withDetail(String detail) {
    return new PhoneNotFoundException(DEFAULT_MESSAGE + "\n세부정보: " + detail);
  }
}