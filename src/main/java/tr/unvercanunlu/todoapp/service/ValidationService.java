package tr.unvercanunlu.todoapp.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationService {

  private final DateTimeService dateTimeService;

  public boolean validateId(Long id) {
    return (id == null) || (id <= 0);
  }

  public boolean validateTask(String task) {
    return (task == null) || task.isBlank();
  }

  public boolean validateDueDate(LocalDateTime dueDate) {
    if (dueDate == null) {
      return false;
    }

    LocalDateTime now = dateTimeService.nowUtc();
    return dueDate.isBefore(now);
  }

}
