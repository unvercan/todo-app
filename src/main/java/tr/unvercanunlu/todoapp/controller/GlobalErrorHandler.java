package tr.unvercanunlu.todoapp.controller;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import tr.unvercanunlu.todoapp.exception.ApplicationException;
import tr.unvercanunlu.todoapp.exception.DueDateNotValidException;
import tr.unvercanunlu.todoapp.exception.IdNotValidException;
import tr.unvercanunlu.todoapp.exception.TaskNotValidException;
import tr.unvercanunlu.todoapp.exception.ToDoNotFoundException;
import tr.unvercanunlu.todoapp.model.ErrorResponse;

@ControllerAdvice
public class GlobalErrorHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({IdNotValidException.class, TaskNotValidException.class, DueDateNotValidException.class, IllegalArgumentException.class})
  public ErrorResponse handleBadRequest(ApplicationException exception) {
    return new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        exception.getReason(),
        Objects.toString(exception.getData(), "null")
    );
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ToDoNotFoundException.class)
  public ErrorResponse handleNotFound(ApplicationException exception) {
    return new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        exception.getReason(),
        Objects.toString(exception.getData(), "null")
    );
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ErrorResponse handleOthers(Exception exception) {
    return new ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "An error occurred",
        exception.getMessage()
    );
  }

}
