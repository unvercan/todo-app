package tr.unvercanunlu.todoapp.exception;

import java.util.Objects;

public class DueDateNotValidException extends ApplicationException {

  public DueDateNotValidException(Object data) {
    super("Due Date not valid: %s".formatted(Objects.toString(data, "")), data);
  }

}
