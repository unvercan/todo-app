package tr.unvercanunlu.todoapp.controller;

import static tr.unvercanunlu.todoapp.config.AuthConfig.ROLE_HEADER;
import static tr.unvercanunlu.todoapp.config.AuthConfig.ROLE_USER;
import static tr.unvercanunlu.todoapp.config.AuthConfig.TOKEN_HEADER;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.unvercanunlu.todoapp.exception.DueDateNotValidException;
import tr.unvercanunlu.todoapp.exception.IdNotValidException;
import tr.unvercanunlu.todoapp.exception.TaskNotValidException;
import tr.unvercanunlu.todoapp.exception.ToDoNotFoundException;
import tr.unvercanunlu.todoapp.model.ToDo;
import tr.unvercanunlu.todoapp.model.ToDoRequest;
import tr.unvercanunlu.todoapp.service.ToDoService;
import tr.unvercanunlu.todoapp.util.AuthUtil;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class ToDoController {

  private final ToDoService toDoService;
  private final AuthUtil authUtil;

  @PostMapping
  public ResponseEntity<ToDo> createToDo(@RequestBody ToDoRequest request) throws DueDateNotValidException, TaskNotValidException {
    ToDo created = toDoService.createToDo(request);

    URI location = URI.create("/todos/%d".formatted(created.getId()));

    return ResponseEntity.status(HttpStatus.CREATED)
        .location(location)
        .body(created);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ToDo> retrieveToDo(@PathVariable Long id) throws ToDoNotFoundException, IdNotValidException {
    ToDo retrieved = toDoService.findToDoById(id);

    return ResponseEntity.status(HttpStatus.OK)
        .body(retrieved);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDoRequest request,
      @RequestHeader(name = TOKEN_HEADER, required = false) String token,
      @CookieValue(name = ROLE_HEADER, defaultValue = ROLE_USER, required = false) String role)
      throws ToDoNotFoundException, DueDateNotValidException, TaskNotValidException, IdNotValidException {

    // HTTP request header / HTTP cookie based authorization control
    if ((authUtil != null) && (!(authUtil.isTokenMatched(token) || authUtil.isRoleMatched(role)))) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .build();
    }

    ToDo updated = toDoService.updateToDo(id, request);

    return ResponseEntity.status(HttpStatus.OK)
        .body(updated);
  }

  @PatchMapping("/{id}/mark-done")
  public ResponseEntity<ToDo> markToDoDone(@PathVariable Long id) throws ToDoNotFoundException, IdNotValidException {
    ToDo updated = toDoService.markToDo(id, true);

    return ResponseEntity.status(HttpStatus.OK)
        .body(updated);
  }

  @PatchMapping("/{id}/mark-undone")
  public ResponseEntity<ToDo> markToDoUnDone(@PathVariable Long id) throws ToDoNotFoundException, IdNotValidException {
    ToDo updated = toDoService.markToDo(id, false);

    return ResponseEntity.status(HttpStatus.OK)
        .body(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteToDo(@PathVariable Long id,
      @RequestHeader(name = TOKEN_HEADER, required = false) String token,
      @CookieValue(name = ROLE_HEADER, defaultValue = ROLE_USER, required = false) String role) throws IdNotValidException {

    // HTTP request header / HTTP cookie based authorization control
    if ((authUtil != null) && (!(authUtil.isTokenMatched(token) || authUtil.isRoleMatched(role)))) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .build();
    }

    toDoService.deleteToDo(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
