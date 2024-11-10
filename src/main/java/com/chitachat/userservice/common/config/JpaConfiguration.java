package com.chitachat.userservice.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.chitachat.userservice.**.repository")
@EnableJpaAuditing
public class JpaConfiguration {

}
