package com.chitachat.userservice.common.config;

import com.chitachat.userservice.jwt.JWTUtil;
import com.chitachat.userservice.user.model.entity.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JWTUtil jwtUtil;

  public CustomSuccessHandler(JWTUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    UserDTO userDTO = (UserDTO) authentication.getPrincipal();
    String username = userDTO.getUsername();

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    GrantedAuthority auth = iterator.next();
    String role = auth.getAuthority();

    String token = jwtUtil.createJwt(username, role, 60*60*1000L);

    response.addCookie(createCookie("Authorization", token));
    response.sendRedirect("http://localhost:8999/user-info");
  }

  private Cookie createCookie(String key, String value) {

    Cookie cookie = new Cookie(key, value);
    cookie.setMaxAge(60*60*1000);
    //cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setHttpOnly(true);

    return cookie;
  }
}