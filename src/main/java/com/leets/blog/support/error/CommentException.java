package com.leets.blog.support.error;

import com.leets.blog.support.error.ErrorType;

public class CommentException extends RuntimeException {

  private final ErrorType errorType;
  private final Object data;

  public CommentException(ErrorType errorType) {
    super(errorType.getMessage());
    this.errorType = errorType;
    this.data = null;
  }

  public CommentException(ErrorType errorType, Object data) {
    super(errorType.getMessage());
    this.errorType = errorType;
    this.data = data;
  }

  public ErrorType getErrorType() { return errorType; }
  public Object getData() { return data; }
}