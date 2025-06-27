package tr.unvercanunlu.todoapp.model;

import java.time.LocalDateTime;

public record ToDoRequest(
    String task,
    Boolean completed,
    LocalDateTime dueDate
) {

}
