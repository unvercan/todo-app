package tr.unvercanunlu.todoapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  public ToDo createToDo(@RequestBody ToDoRequest request) {
    return toDoService.createToDo(request);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ToDo retrieveToDo(@PathVariable Long id) {
    return toDoService.findToDoById(id);
  }

  @PostMapping("/{id}/mark-done")
  @ResponseStatus(HttpStatus.OK)
  public ToDo markToDoDone(@PathVariable Long id) {
    return toDoService.updateToDo(id, true);
  }

  @PostMapping("/{id}/mark-undone")
  @ResponseStatus(HttpStatus.OK)
  public ToDo markToDoUnDone(@PathVariable Long id) {
    return toDoService.updateToDo(id, false);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteToDo(@PathVariable Long id) {
    toDoService.deleteToDo(id);
  }

}
