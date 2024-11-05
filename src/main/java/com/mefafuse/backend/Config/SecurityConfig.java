package com.mefafuse.backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsConfig corsConfig;

    public SecurityConfig(CorsConfig corsConfig) {
        this.corsConfig = corsConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource())) // CORS 설정 적용
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/**").permitAll() // 모든 POST 요청 허용
                        .requestMatchers(HttpMethod.GET, "/**").permitAll() // 모든 GET 요청 허용
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // 모든 OPTIONS 요청 허용
                        .anyRequest().permitAll() // 그 외 모든 요청 허용
                );

        return http.build();
    }
}
