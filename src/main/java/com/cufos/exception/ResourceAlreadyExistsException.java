package com.cufos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResourceAlreadyExistsException extends RuntimeException {

  private static final long serialVersionUDI = 2L;

  // Exception when not founding a resource. Output with custom message.
  public ResourceAlreadyExistsException(String msg) {
    super(msg);
  }
}