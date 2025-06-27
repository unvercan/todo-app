package tr.unvercanunlu.todoapp.util;

import lombok.RequiredArgsConstructor;
import tr.unvercanunlu.todoapp.repository.ToDoRepository;

@RequiredArgsConstructor
public class IdUtil {

  private final ToDoRepository toDoRepository;

  public long generateId() {
    return toDoRepository.findTopByOrderByIdDesc()
        .map(document -> document.getLong("_id"))
        .map(id -> id + 1)
        .orElse(1L);
  }

}
