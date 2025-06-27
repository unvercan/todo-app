package tr.unvercanunlu.todoapp.service;

import org.springframework.stereotype.Component;

@Component
public class ValidationService {

  public boolean validateId(Long id) {
    return (id == null) || (id <= 0);
  }

  public boolean validateTask(String task) {
    return (task == null) || task.isBlank();
  }

}
