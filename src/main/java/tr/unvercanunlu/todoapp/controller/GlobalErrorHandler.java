package tr.unvercanunlu.todoapp.controller;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tr.unvercanunlu.todoapp.exception.ApplicationException;
import tr.unvercanunlu.todoapp.exception.DueDateNotValidException;
import tr.unvercanunlu.todoapp.exception.IdNotValidException;
import tr.unvercanunlu.todoapp.exception.TaskNotValidException;
import tr.unvercanunlu.todoapp.exception.ToDoNotFoundException;
import tr.unvercanunlu.todoapp.model.ErrorResponse;

@ControllerAdvice
public class GlobalErrorHandler {

  @ExceptionHandler({IdNotValidException.class, TaskNotValidException.class, DueDateNotValidException.class})
  public ResponseEntity<ErrorResponse> handleBadRequest(ApplicationException exception) {
    ErrorResponse response = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        exception.getReason(),
        Objects.toString(exception.getData(), "null")
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(response);
  }

  @ExceptionHandler(ToDoNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(ToDoNotFoundException exception) {
    ErrorResponse response = new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        exception.getReason(),
        Objects.toString(exception.getData(), "null")
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(Exception exception) {
    ErrorResponse response = new ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "An unexpected error occurred",
        exception.getMessage()
    );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(response);
  }
}
