package tr.unvercanunlu.todoapp.util;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidationUtil {

  private final DateTimeUtil dateTimeUtil;

  public boolean validateId(Long id) {
    return (id == null) || (id <= 0);
  }

  public boolean validateTask(String task) {
    return (task == null) || task.isBlank();
  }

  public boolean isDueDateInPast(LocalDateTime dueDate) {
    if (dueDate == null) {
      return false;
    }

    LocalDateTime now = dateTimeUtil.nowUtc();
    return dueDate.isBefore(now);
  }

}
