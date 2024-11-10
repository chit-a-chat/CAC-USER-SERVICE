package com.chitachat.userservice.user.repository;

import com.chitachat.userservice.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

}
