package tr.unvercanunlu.todoapp.exception;

public class DueDateNotValidException extends ApplicationException {

  public DueDateNotValidException(Object data) {
    super("Due Date not valid", data);
  }

}
