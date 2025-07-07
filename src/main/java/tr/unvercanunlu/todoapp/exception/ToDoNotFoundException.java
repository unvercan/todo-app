package tr.unvercanunlu.todoapp.exception;

import java.util.Objects;

public class ToDoNotFoundException extends ApplicationException {

  public ToDoNotFoundException(Object data) {
    super("ToDo not found: %s".formatted(Objects.toString(data, "")), data);
  }

}
