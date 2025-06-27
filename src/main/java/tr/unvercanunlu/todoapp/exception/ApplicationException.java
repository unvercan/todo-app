package tr.unvercanunlu.todoapp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class ApplicationException extends RuntimeException {

  private final String reason;
  private final Object data;

}
