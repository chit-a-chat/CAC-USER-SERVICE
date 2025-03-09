package com.chitachat.userservice.common.config;

import com.chitachat.userservice.user.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      CustomOauth2UserService customOauth2UserService) throws Exception {
    //접근 권한 설정
    http
        .csrf(AbstractHttpConfigurer::disable)
        .headers(headers -> headers
            .frameOptions(FrameOptionsConfig::sameOrigin)
        )
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/", "/index.html", "/oauth2/**", "/h2-console/**", "/templates/**").permitAll()
            .requestMatchers("/user-info").authenticated()
            .anyRequest().authenticated()
        )
        .oauth2Login(oauth2 -> oauth2
            .loginPage("/")
            .defaultSuccessUrl("/user-info", true)
            .userInfoEndpoint(userInfo -> userInfo.userService(customOauth2UserService))
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID"));
    return http.build();
  }
}
