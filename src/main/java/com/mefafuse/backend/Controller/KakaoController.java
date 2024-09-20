package com.mefafuse.backend.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mefafuse.backend.Entity.KakaoUser;
import com.mefafuse.backend.Repository.KakaoUserRepository;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class KakaoController {

    private final RestTemplate restTemplate;
    private final KakaoUserRepository kakaoUserRepository;
    private final ObjectMapper objectMapper; // JSON 처리를 위한 ObjectMapper

    public KakaoController(RestTemplate restTemplate, KakaoUserRepository kakaoUserRepository, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.kakaoUserRepository = kakaoUserRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {
        String accessTokenResponse = getAccessToken(code);
        if (accessTokenResponse == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get access token");
        }

        String accessToken = extractAccessToken(accessTokenResponse);
        if (accessToken == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get access token");
        }

        // 사용자 정보 요청 및 DB에 저장
        String userInfo = getUserInfo(accessToken);
        saveOrUpdateKakaoUser(userInfo);

        return ResponseEntity.ok(userInfo);
    }

    private String extractAccessToken(String response) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            return (String) responseMap.get("access_token");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getUserInfo(String accessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    private void saveOrUpdateKakaoUser(String userInfo) {
        JSONObject jsonObject = new JSONObject(userInfo);
        Long kakaoId = jsonObject.getLong("id");
        String nickname = jsonObject.getJSONObject("properties").getString("nickname");
        String profileImage = jsonObject.getJSONObject("properties").optString("profile_image", null);

        Optional<KakaoUser> existingUser = kakaoUserRepository.findByKakaoId(kakaoId);
        KakaoUser user;

        if (existingUser.isPresent()) {
            user = existingUser.get();
            user.setNickname(nickname);
            user.setProfileImage(profileImage);
        } else {
            user = new KakaoUser();
            user.setKakaoId(kakaoId);
            user.setNickname(nickname);
            user.setProfileImage(profileImage);
        }

        kakaoUserRepository.save(user);
    }

    public String getAccessToken(String code) {
        String url = "https://kauth.kakao.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "39a096f2c5fa71cb1ffde623e22d201b");
        params.add("redirect_uri", "http://localhost:8080/kakao/callback");
        params.add("code", code);
        params.add("client_secret", "AL8c4rXT3cH35L68qViBIPH6VvJRGjUR"); // 필요 시 추가
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error in getAccessToken: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        }
    }
    @GetMapping("/api/check-user")
    public ResponseEntity<Map<String, Boolean>> checkUser(@RequestParam Long kakaoId) {
        Optional<KakaoUser> user = kakaoUserRepository.findByKakaoId(kakaoId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", user.isPresent());
        return ResponseEntity.ok(response);
    }


}