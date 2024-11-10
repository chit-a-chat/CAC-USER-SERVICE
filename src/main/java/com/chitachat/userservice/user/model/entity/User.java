package com.chitachat.userservice.user.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Table(name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String userId;
    private String name;

}
