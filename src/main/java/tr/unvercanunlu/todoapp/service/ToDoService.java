package tr.unvercanunlu.todoapp.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import tr.unvercanunlu.todoapp.exception.IdNotValidException;
import tr.unvercanunlu.todoapp.exception.TaskNotValidException;
import tr.unvercanunlu.todoapp.exception.ToDoNotFoundException;
import tr.unvercanunlu.todoapp.model.ToDo;
import tr.unvercanunlu.todoapp.model.ToDoRequest;

@Service
@RequiredArgsConstructor
public class ToDoService {

  private final MongoRepository<ToDo, Long> toDoRepository;
  private final ValidationService validationService;
  private final DateTimeService dateTimeService;

  public ToDo createToDo(ToDoRequest request) throws TaskNotValidException {
    if ((request == null) || validationService.validateTask(request.task())) {
      throw new TaskNotValidException(request);
    }

    String task = Optional.of(request)
        .map(ToDoRequest::task)
        .map(String::strip)
        .orElse("");

    boolean completed = Optional.of(request)
        .map(ToDoRequest::completed)
        .orElse(false);

    LocalDateTime now = dateTimeService.nowUtc();

    ToDo toDo = ToDo.builder()
        .task(task)
        .completed(completed)
        .createdAt(now)
        .build();

    return toDoRepository.save(toDo);
  }

  public ToDo findToDoById(Long id) throws IdNotValidException, ToDoNotFoundException {
    if (validationService.validateId(id)) {
      throw new IdNotValidException(id);
    }

    return toDoRepository.findById(id)
        .orElseThrow(() -> new ToDoNotFoundException(id));
  }

  public ToDo updateToDo(Long id, boolean completed) throws IdNotValidException, ToDoNotFoundException {
    if (validationService.validateId(id)) {
      throw new IdNotValidException(id);
    }

    ToDo toDo = toDoRepository.findById(id)
        .orElseThrow(() -> new ToDoNotFoundException(id));

    LocalDateTime now = dateTimeService.nowUtc();

    toDo.setCompleted(completed);
    toDo.setUpdatedAt(now);

    return toDoRepository.save(toDo);
  }

  public void deleteToDo(Long id) throws IdNotValidException {
    if (validationService.validateId(id)) {
      throw new IdNotValidException(id);
    }

    toDoRepository.deleteById(id);
  }

}
