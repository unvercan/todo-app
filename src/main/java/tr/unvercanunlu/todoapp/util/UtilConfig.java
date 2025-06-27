package tr.unvercanunlu.todoapp.util;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tr.unvercanunlu.todoapp.repository.ToDoRepository;

@Configuration
public class UtilConfig {

  @Bean
  public DateTimeUtil dateTimeUtil() {
    return new DateTimeUtil();
  }

  @Bean
  public ValidationUtil validationUtil(DateTimeUtil dateTimeUtil) {
    return new ValidationUtil(dateTimeUtil);
  }

  @Bean
  public IdUtil idUtil(ToDoRepository toDoRepository) {
    return new IdUtil(toDoRepository);
  }

  @Bean
  @ConditionalOnProperty(name = "auth.enabled", havingValue = "true")
  public AuthUtil authUtil() {
    return new AuthUtil();
  }

}
