package com.chitachat.userservice.user.model.entity;

import java.util.Map;

public class GoogleUserDetail implements OAuth2UserInfo {

  private final Map<String, Object> attributes;

  public GoogleUserDetail(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getName() {
    return (String) attributes.get("name");
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }

  @Override
  public String getProviderId() {
    return (String) attributes.get("sub");
  }

  @Override
  public String getProvider() {
    return "google";
  }
}
