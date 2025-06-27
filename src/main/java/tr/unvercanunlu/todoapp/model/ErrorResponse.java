package tr.unvercanunlu.todoapp.model;

public record ErrorResponse(
    int code,
    String description,
    String data
) {

}
