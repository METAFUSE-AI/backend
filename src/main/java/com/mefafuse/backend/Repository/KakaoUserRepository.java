package com.mefafuse.backend.Repository;

import com.mefafuse.backend.Entity.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoUserRepository extends JpaRepository<KakaoUser, Long> {
    Optional<KakaoUser> findByKakaoId(Long kakaoId);
}
