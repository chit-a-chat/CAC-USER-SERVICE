package com.chitachat.userservice.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserRestController {

  // 홈 페이지 (로그인 상태에 따라 index.html 또는 user-info.html로 이동)
  @GetMapping("/")
  public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
    if (principal != null) {
      model.addAttribute("user", principal.getAttributes()); // 로그인된 사용자 정보 추가
      return "redirect:/userInfo";
    }
    return "index"; // 로그인 안 된 경우 index.html 반환
  }

  // 사용자 정보 페이지
  @GetMapping("/user-info")
  public String userInfo(@AuthenticationPrincipal OAuth2User principal, Model model) {
    if (principal == null) {
      return "redirect:/"; // 로그인 안 됐으면 홈으로 리다이렉트
    }
    model.addAttribute("user", principal.getAttributes()); // 사용자 정보 Thymeleaf로 전달
    return "userInfo";
  }
}
