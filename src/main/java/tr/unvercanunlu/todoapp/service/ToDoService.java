package tr.unvercanunlu.todoapp.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import tr.unvercanunlu.todoapp.exception.DueDateNotValidException;
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

  public ToDo createToDo(ToDoRequest request) throws TaskNotValidException, DueDateNotValidException, IllegalArgumentException {
    if (request == null) {
      throw new IllegalArgumentException("ToDo is null!");
    }

    if (validationService.validateTask(request.task())) {
      throw new TaskNotValidException(request.task());
    }

    String task = request.task().strip();

    boolean completed = Optional.of(request)
        .map(ToDoRequest::completed)
        .orElse(false);

    LocalDateTime now = dateTimeService.nowUtc();

    if (validationService.validateDueDate(request.dueDate())) {
      throw new DueDateNotValidException(request.dueDate());
    }

    ToDo toDo = ToDo.builder()
        .task(task)
        .completed(completed)
        .dueDate(request.dueDate())
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

  public ToDo markToDo(Long id, boolean completed) throws IdNotValidException, ToDoNotFoundException {
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

  public ToDo updateToDo(Long id, ToDoRequest request) throws IdNotValidException, ToDoNotFoundException, DueDateNotValidException {
    if (request == null) {
      throw new IllegalArgumentException("ToDo is null!");
    }

    if (validationService.validateId(id)) {
      throw new IdNotValidException(id);
    }

    if (validationService.validateTask(request.task())) {
      throw new TaskNotValidException(request.task());
    }

    ToDo toDo = toDoRepository.findById(id)
        .orElseThrow(() -> new ToDoNotFoundException(id));

    String task = request.task().strip();

    boolean completed = Optional.of(request)
        .map(ToDoRequest::completed)
        .orElse(false);

    LocalDateTime now = dateTimeService.nowUtc();

    if (validationService.validateDueDate(request.dueDate())) {
      throw new DueDateNotValidException(request.dueDate());
    }

    toDo.setTask(task);
    toDo.setCompleted(completed);
    toDo.setDueDate(request.dueDate());
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
