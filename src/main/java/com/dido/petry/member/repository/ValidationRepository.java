package com.dido.petry.member.repository;

import com.dido.petry.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
    Member findByEmail(String email);
}
