package com.dido.petry.member.repository;

import com.dido.petry.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    Optional<Member> findByNameAndEmail(String name, String email);

    Optional<Member> findByUsernameAndEmail(String username, String email);

    void deleteByUsername(String username);

}
