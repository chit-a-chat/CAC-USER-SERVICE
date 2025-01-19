package com.chitachat.userservice.user.model.entity;

public interface OAuth2UserInfo {
  String getName();
  String getEmail();
  String getProviderId();
  String getProvider();
}
