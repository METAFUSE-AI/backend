package com.mefafuse.backend.Repository;

import com.mefafuse.backend.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberIdRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long memberid);
}
