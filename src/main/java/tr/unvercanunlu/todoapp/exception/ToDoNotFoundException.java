package tr.unvercanunlu.todoapp.exception;

public class ToDoNotFoundException extends ApplicationException {

  public ToDoNotFoundException(Object data) {
    super("ToDo not found", data);
  }

}
