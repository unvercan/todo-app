package tr.unvercanunlu.todoapp.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthConfig {

  // token for HTTP request header
  public static final String TOKEN = "f995e63b-173f-44ce-855d-05bd3034567e";
  public static final String TOKEN_HEADER = "X-TOKEN";

  // role for HTTP cookie
  public static final String ROLE_USER = "user";
  public static final String ROLE_ADMIN = "admin";
  public static final String ROLE_HEADER = "role";

}
