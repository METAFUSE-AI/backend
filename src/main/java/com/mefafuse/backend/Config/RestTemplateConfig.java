package com.mefafuse.backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // 기본 HttpMessageConverter 설정
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();

        // FormHttpMessageConverter 추가
        messageConverters.add(new FormHttpMessageConverter());

        // 설정된 메시지 변환기 적용
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }
}
