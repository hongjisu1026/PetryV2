package com.dido.petry.repository;

import com.dido.petry.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    Member findByNameAndEmail(String name, String email);

    Member findByUsernameAndEmail(String username, String email);

}
