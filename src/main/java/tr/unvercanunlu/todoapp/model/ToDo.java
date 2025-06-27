package tr.unvercanunlu.todoapp.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "toDos")
public class ToDo {

  @Id
  private long id;

  private String task;
  private boolean completed;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
