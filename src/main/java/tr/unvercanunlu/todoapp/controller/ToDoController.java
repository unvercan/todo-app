package tr.unvercanunlu.todoapp.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public ResponseEntity<ToDo> createToDo(@RequestBody ToDoRequest request) {
    ToDo created = toDoService.createToDo(request);

    URI location = URI.create("/todos/%d".formatted(created.getId()));

    return ResponseEntity.status(HttpStatus.CREATED)
        .location(location)
        .body(created);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ToDo> retrieveToDo(@PathVariable Long id) {
    ToDo retrieved = toDoService.findToDoById(id);

    return ResponseEntity.status(HttpStatus.OK)
        .body(retrieved);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDoRequest request) {
    ToDo updated = toDoService.updateToDo(id, request);

    return ResponseEntity.status(HttpStatus.OK)
        .body(updated);
  }

  @PatchMapping("/{id}/mark-done")
  public ResponseEntity<ToDo> markToDoDone(@PathVariable Long id) {
    ToDo updated = toDoService.markToDo(id, true);

    return ResponseEntity.status(HttpStatus.OK)
        .body(updated);
  }

  @PatchMapping("/{id}/mark-undone")
  public ResponseEntity<ToDo> markToDoUnDone(@PathVariable Long id) {
    ToDo updated = toDoService.markToDo(id, false);

    return ResponseEntity.status(HttpStatus.OK)
        .body(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteToDo(@PathVariable Long id) {
    toDoService.deleteToDo(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
