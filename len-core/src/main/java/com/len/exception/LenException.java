package com.len.exception;


public class LenException extends RuntimeException {

  private String message;

  public LenException(String message){
    super(message);
    this.message=message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
