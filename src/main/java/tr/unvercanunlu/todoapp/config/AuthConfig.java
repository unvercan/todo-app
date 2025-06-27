package tr.unvercanunlu.todoapp.config;

public interface AuthConfig {

  // token for HTTP request header
  String TOKEN = "f995e63b-173f-44ce-855d-05bd3034567e";
  String TOKEN_HEADER = "X-TOKEN";

  // role for HTTP cookie
  String ROLE_USER = "user";
  String ROLE_ADMIN = "admin";
  String ROLE_HEADER = "role";

}
