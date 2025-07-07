package tr.unvercanunlu.todoapp.exception;

import java.util.Objects;

public class IdNotValidException extends ApplicationException {

  public IdNotValidException(Object data) {
    super("ID not valid: %s".formatted(Objects.toString(data, "")), data);
  }

}
