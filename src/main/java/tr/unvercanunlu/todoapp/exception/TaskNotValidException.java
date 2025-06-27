package tr.unvercanunlu.todoapp.exception;

public class TaskNotValidException extends ApplicationException {

  public TaskNotValidException(Object data) {
    super("Task not valid", data);
  }

}
