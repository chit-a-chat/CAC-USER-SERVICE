package com.chitachat.userservice.user.controller;

import com.chitachat.userservice.user.model.entity.UserDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping("/")
  public String main() {
    return "index";
  }

  @GetMapping("/user-info")
  public String userInfo(@AuthenticationPrincipal UserDTO user, Model model) {
    model.addAttribute("username", user.getUsername());
    model.addAttribute("email", user.getUsername());

    return "userInfo";
  }
}
