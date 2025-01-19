package com.chitachat.userservice.user.service;

import com.chitachat.userservice.user.model.entity.CustomOauth2UserDetails;
import com.chitachat.userservice.user.model.entity.GoogleUserDetail;
import com.chitachat.userservice.user.model.entity.OAuth2UserInfo;
import com.chitachat.userservice.user.model.entity.User;
import com.chitachat.userservice.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

  private final UserJpaRepository userJpaRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);
    log.info("getAttributes : {}", oAuth2User.getAttributes());

    String provider = userRequest.getClientRegistration().getRegistrationId();

    OAuth2UserInfo oAuth2UserInfo = null;

    if (provider.equals("google")) {
      oAuth2UserInfo = new GoogleUserDetail(oAuth2User.getAttributes());
    }

    assert oAuth2UserInfo != null;
    String providerId = oAuth2UserInfo.getProviderId();
    String loginId = provider + "_" + providerId;
    String name = oAuth2UserInfo.getName();

    User fineUser = userJpaRepository.findByLoginId(loginId);
    User user;

    if (fineUser == null) {
      user = User.builder().
          loginId(loginId).
          name(name).
          providerId(providerId).
          provider(provider).
          build();
      userJpaRepository.save(user);
    } else {
      user = fineUser;
    }
    return new CustomOauth2UserDetails(user, oAuth2User.getAttributes());
  }

}