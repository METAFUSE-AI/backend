// MemberRepository 인터페이스
package com.mefafuse.backend.Repository;

import com.mefafuse.backend.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByUsername(String username);
    Optional<Member> findByUsername(String username);
}
