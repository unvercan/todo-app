package tr.unvercanunlu.todoapp.model;

public record ToDoRequest(
    String task,
    Boolean completed
) {

}
