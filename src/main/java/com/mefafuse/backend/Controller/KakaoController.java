package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.KakaoTokenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth2/kakao")
public class KakaoController {

    private static final Logger logger = LoggerFactory.getLogger(KakaoController.class);

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @Value("${kakao.redirect.uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/callback")
    public ResponseEntity<String> kakaoCallback(@RequestBody KakaoTokenRequest tokenRequest) {
        String code = tokenRequest.getCode();
        if (code == null || code.isEmpty()) {
            return ResponseEntity.badRequest().body("Authorization code is missing");
        }
        logger.info("Received authorization code: {}", code);

        // 액세스 토큰 요청
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> tokenParams = new HashMap<>();
        tokenParams.put("grant_type", "authorization_code");
        tokenParams.put("client_id", clientId);
        tokenParams.put("redirect_uri", redirectUri);
        tokenParams.put("code", code);
        tokenParams.put("client_secret", clientSecret);

        HttpEntity<Map<String, String>> tokenRequestEntity = new HttpEntity<>(tokenParams, headers);
        try {
            ResponseEntity<Map> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenRequestEntity, Map.class);

            if (!tokenResponse.getStatusCode().is2xxSuccessful()) {
                logger.error("Failed to get access token. Status code: {}", tokenResponse.getStatusCode());
                return ResponseEntity.status(tokenResponse.getStatusCode()).body("Failed to get access token");
            }

            // 액세스 토큰 추출
            Map<String, Object> tokenBody = tokenResponse.getBody();
            String accessToken = (String) tokenBody.get("access_token");
            logger.info("Received access token: {}", accessToken);

            // 액세스 토큰을 이용해 사용자 정보 요청
            String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
            HttpHeaders userHeaders = new HttpHeaders();
            userHeaders.setBearerAuth(accessToken);

            HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);
            ResponseEntity<Map> userResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userRequest, Map.class);

            return ResponseEntity.ok(userResponse.getBody().toString());

        } catch (Exception e) {
            logger.error("Error during token request", e);
            return ResponseEntity.status(500).body("Error during token request");
        }
    }
}
