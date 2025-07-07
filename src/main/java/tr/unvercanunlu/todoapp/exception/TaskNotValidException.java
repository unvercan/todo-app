package tr.unvercanunlu.todoapp.exception;

import java.util.Objects;

public class TaskNotValidException extends ApplicationException {

  public TaskNotValidException(Object data) {
    super("Task not valid: %s".formatted(Objects.toString(data, "")), data);
  }

}
