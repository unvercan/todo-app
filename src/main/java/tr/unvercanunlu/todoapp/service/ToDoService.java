package tr.unvercanunlu.todoapp.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.unvercanunlu.todoapp.exception.DueDateNotValidException;
import tr.unvercanunlu.todoapp.exception.IdNotValidException;
import tr.unvercanunlu.todoapp.exception.TaskNotValidException;
import tr.unvercanunlu.todoapp.exception.ToDoNotFoundException;
import tr.unvercanunlu.todoapp.model.ToDo;
import tr.unvercanunlu.todoapp.model.ToDoRequest;
import tr.unvercanunlu.todoapp.repository.ToDoRepository;
import tr.unvercanunlu.todoapp.util.DateTimeUtil;
import tr.unvercanunlu.todoapp.util.IdUtil;
import tr.unvercanunlu.todoapp.util.ValidationUtil;

@Service
@RequiredArgsConstructor
public class ToDoService {

  private final ToDoRepository toDoRepository;
  private final ValidationUtil validationUtil;
  private final DateTimeUtil dateTimeUtil;
  private final IdUtil idUtil;

  public ToDo createToDo(ToDoRequest request) throws TaskNotValidException, DueDateNotValidException, IllegalArgumentException {
    if (request == null) {
      throw new IllegalArgumentException("ToDo is null!");
    }

    if (validationUtil.validateTask(request.task())) {
      throw new TaskNotValidException(request.task());
    }

    String task = request.task().strip();

    boolean completed = Optional.of(request)
        .map(ToDoRequest::completed)
        .orElse(false);

    LocalDateTime now = dateTimeUtil.nowUtc();

    if (!completed && validationUtil.isDueDateInPast(request.dueDate())) {
      throw new DueDateNotValidException(request.dueDate());
    }

    long id = idUtil.generateId();

    ToDo toDo = ToDo.builder()
        .id(id)
        .task(task)
        .completed(completed)
        .dueDate(request.dueDate())
        .createdAt(now)
        .build();

    return toDoRepository.save(toDo);
  }

  public ToDo findToDoById(Long id) throws IdNotValidException, ToDoNotFoundException {
    if (validationUtil.validateId(id)) {
      throw new IdNotValidException(id);
    }

    return toDoRepository.findById(id)
        .orElseThrow(() -> new ToDoNotFoundException(id));
  }

  public ToDo markToDo(Long id, boolean completed) throws IdNotValidException, ToDoNotFoundException {
    if (validationUtil.validateId(id)) {
      throw new IdNotValidException(id);
    }

    ToDo toDo = toDoRepository.findById(id)
        .orElseThrow(() -> new ToDoNotFoundException(id));

    LocalDateTime now = dateTimeUtil.nowUtc();

    toDo.setCompleted(completed);
    toDo.setUpdatedAt(now);

    return toDoRepository.save(toDo);
  }

  public ToDo updateToDo(Long id, ToDoRequest request) throws IdNotValidException, ToDoNotFoundException, DueDateNotValidException {
    if (request == null) {
      throw new IllegalArgumentException("ToDo is null!");
    }

    if (validationUtil.validateId(id)) {
      throw new IdNotValidException(id);
    }

    if (validationUtil.validateTask(request.task())) {
      throw new TaskNotValidException(request.task());
    }

    ToDo toDo = toDoRepository.findById(id)
        .orElseThrow(() -> new ToDoNotFoundException(id));

    String task = request.task().strip();

    boolean completed = Optional.of(request)
        .map(ToDoRequest::completed)
        .orElse(false);

    LocalDateTime now = dateTimeUtil.nowUtc();

    if (!completed && validationUtil.isDueDateInPast(request.dueDate())) {
      throw new DueDateNotValidException(request.dueDate());
    }

    toDo.setTask(task);
    toDo.setCompleted(completed);
    toDo.setDueDate(request.dueDate());
    toDo.setUpdatedAt(now);

    return toDoRepository.save(toDo);
  }

  public void deleteToDo(Long id) throws IdNotValidException {
    if (validationUtil.validateId(id)) {
      throw new IdNotValidException(id);
    }

    toDoRepository.deleteById(id);
  }

}
