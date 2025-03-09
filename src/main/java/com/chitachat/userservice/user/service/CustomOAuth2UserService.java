package com.chitachat.userservice.user.service;

import com.chitachat.userservice.jwt.JWTUtil;
import com.chitachat.userservice.user.model.entity.GoogleUserDetail;
import com.chitachat.userservice.user.model.entity.OAuth2UserInfo;
import com.chitachat.userservice.user.model.entity.UserDTO;
import com.chitachat.userservice.user.model.entity.UserEntity;
import com.chitachat.userservice.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;
  private final JWTUtil jwtUtil;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);
    log.info("oAuth2User attributes = {}", oAuth2User.getAttributes());

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    OAuth2UserInfo oAuth2UserInfo;

    if ("google".equals(registrationId)) {
      oAuth2UserInfo = new GoogleUserDetail(oAuth2User.getAttributes());
    } else {
      throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인입니다: " + registrationId);
    }

    String username = oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId();
    UserEntity userEntity = userRepository.findByUsername(username);

    if (userEntity == null) {
      userEntity = new UserEntity();
      userEntity.setUsername(username);
      userEntity.setEmail(oAuth2UserInfo.getEmail());
      userEntity.setName(oAuth2UserInfo.getName());
      userEntity.setRole("ROLE_USER");

      userRepository.save(userEntity);
      log.info("신규 회원 등록: {}", username);
    } else {
      boolean needUpdate = false;

      if (!userEntity.getEmail().equals(oAuth2UserInfo.getEmail())) {
        userEntity.setEmail(oAuth2UserInfo.getEmail());
        needUpdate = true;
      }

      if (!userEntity.getName().equals(oAuth2UserInfo.getName())) {
        userEntity.setName(oAuth2UserInfo.getName());
        needUpdate = true;
      }

      if (needUpdate) {
        userRepository.save(userEntity);
        log.info("회원 정보 업데이트: {}", username);
      }
    }

    String token = jwtUtil.createJwt(username, userEntity.getRole(), 60 * 60 * 60L);

    Cookie cookie = new Cookie("Authorization", token);
    cookie.setMaxAge(60 * 60 * 60);
    cookie.setPath("/");
    cookie.setHttpOnly(true);

    // JWT 토큰을 반환 (쿠키는 클라이언트에서 자동으로 저장됨)
    return new UserDTO(
        userEntity.getUsername(),
        userEntity.getRole(),
        oAuth2User.getAttributes()
    );
  }
}
