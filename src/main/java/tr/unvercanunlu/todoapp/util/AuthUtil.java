package tr.unvercanunlu.todoapp.util;

import static tr.unvercanunlu.todoapp.config.AuthConfig.ROLE_ADMIN;
import static tr.unvercanunlu.todoapp.config.AuthConfig.TOKEN;

public class AuthUtil {

  public boolean isTokenMatched(String token) {
    return (token != null) && TOKEN.equals(token.strip());
  }

  public boolean isRoleMatched(String role) {
    return (role != null) && ROLE_ADMIN.equalsIgnoreCase(role.strip());
  }

}
