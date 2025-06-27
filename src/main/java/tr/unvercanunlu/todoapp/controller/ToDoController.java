package tr.unvercanunlu.todoapp.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tr.unvercanunlu.todoapp.model.ToDo;
import tr.unvercanunlu.todoapp.model.ToDoRequest;
import tr.unvercanunlu.todoapp.service.ToDoService;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class ToDoController {

  private final ToDoService toDoService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<ToDo> createToDo(@RequestBody ToDoRequest request) {
    ToDo created = toDoService.createToDo(request);
    URI location = URI.create("/todos/%d".formatted(created.getId()));
    return ResponseEntity.created(location).body(created);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ToDo retrieveToDo(@PathVariable Long id) {
    return toDoService.findToDoById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ToDo updateToDo(@PathVariable Long id, @RequestBody ToDoRequest request) {
    return toDoService.updateToDo(id, request);
  }

  @PostMapping("/{id}/mark-done")
  @ResponseStatus(HttpStatus.OK)
  public ToDo markToDoDone(@PathVariable Long id) {
    return toDoService.markToDo(id, true);
  }

  @PostMapping("/{id}/mark-undone")
  @ResponseStatus(HttpStatus.OK)
  public ToDo markToDoUnDone(@PathVariable Long id) {
    return toDoService.markToDo(id, false);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteToDo(@PathVariable Long id) {
    toDoService.deleteToDo(id);
  }

}
