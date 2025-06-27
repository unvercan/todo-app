package tr.unvercanunlu.todoapp.util;

import static tr.unvercanunlu.todoapp.config.AuthConfig.ROLE_ADMIN;
import static tr.unvercanunlu.todoapp.config.AuthConfig.TOKEN;

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

  public boolean isTokenMatched(String token) {
    return (token != null) && TOKEN.equals(token.strip());
  }

  public boolean isRoleMatched(String role) {
    return (role != null) && ROLE_ADMIN.equalsIgnoreCase(role.strip());
  }

}
