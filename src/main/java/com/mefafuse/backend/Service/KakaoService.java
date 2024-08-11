package com.mefafuse.backend.Service;

import com.mefafuse.backend.Dto.KakaoTokenResponseDto;
import com.mefafuse.backend.Kakao.KakaoApiException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatusCode;

@Slf4j
@Service
public class KakaoService {

    private final String clientId;
    private final String redirectUri;
    private final WebClient webClient;

    private static final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
    private static final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";

    @Autowired
    public KakaoService(WebClient.Builder webClientBuilder,
                        @Value("${kakao.client_id}") String clientId,
                        @Value("${kakao.redirect_uri}") String redirectUri) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.webClient = webClientBuilder.baseUrl(KAUTH_TOKEN_URL_HOST)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }

    public String getAccessTokenFromKakao(String code) {
        log.debug("Attempting to get access token from Kakao with code: {}", code);

        try {
            KakaoTokenResponseDto kakaoTokenResponseDto = webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/oauth/token")
                            .queryParam("grant_type", "authorization_code")
                            .queryParam("client_id", clientId)
                            .queryParam("redirect_uri", redirectUri)
                            .queryParam("code", code)
                            .build())
                    .retrieve()
//                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
//                            Mono.error(new KakaoApiException("Client error: " + clientResponse.statusCode()))
//                    )
//                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
//                            Mono.error(new KakaoApiException("Server error: " + clientResponse.statusCode()))
//                    )
                    .bodyToMono(KakaoTokenResponseDto.class)
                    .block();

            if (kakaoTokenResponseDto == null) {
                throw new KakaoApiException("Failed to receive token response from Kakao");
            }

            log.info("Successfully received access token from Kakao");
            log.debug("Access Token: {}", kakaoTokenResponseDto.getAccessToken());
//            log.debug("Refresh Token: {}", kakaoTokenResponseDto.getRefreshToken());
            log.debug("Id Token: {}", kakaoTokenResponseDto.getIdToken());
//            log.debug("Scope: {}", kakaoTokenResponseDto.getScope());

            return kakaoTokenResponseDto.getAccessToken();
        } catch (Exception e) {
            log.error("Error occurred while getting access token from Kakao", e);
            throw new KakaoApiException("Failed to get access token from Kakao", e);
        }
    }
}



