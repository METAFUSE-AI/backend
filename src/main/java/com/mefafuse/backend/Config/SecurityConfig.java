package com.mefafuse.backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    // CorsConfigurationSource 주입
    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login/**", "/oauth2/**", "/records/**", "/tests/**", "/**").permitAll()  // 특정 경로 모두 허용
                        .anyRequest().authenticated()  // 그 외 경로는 인증 필요
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource));  // CORS 설정

        return http.build();
    }
}
