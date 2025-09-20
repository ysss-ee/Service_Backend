package com.work.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/user/**").permitAll()  // 允许所有 /api/user/** 路径的请求
                        .anyRequest().permitAll()
                )
                .httpBasic(httpBasic -> httpBasic.disable())  // 禁用基本认证
                .formLogin(formLogin -> formLogin.disable()); // 禁用表单登录

        return http.build();
    }
}
