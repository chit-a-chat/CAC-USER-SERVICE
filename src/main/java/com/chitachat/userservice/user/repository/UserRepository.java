package com.chitachat.userservice.user.repository;

import com.chitachat.userservice.user.model.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {


  UserEntity findByUsername(String username);

}