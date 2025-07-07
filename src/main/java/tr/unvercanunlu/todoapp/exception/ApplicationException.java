package tr.unvercanunlu.todoapp.exception;

import lombok.Getter;


public abstract class ApplicationException extends RuntimeException {

  @Getter
  private final Object data;

  protected ApplicationException(String message, Object data) {
    super(message);
    this.data = data;
  }

}
