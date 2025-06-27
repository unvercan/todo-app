package tr.unvercanunlu.todoapp.exception;

public class IdNotValidException extends ApplicationException {

  public IdNotValidException(Object data) {
    super("ID not valid", data);
  }

}
