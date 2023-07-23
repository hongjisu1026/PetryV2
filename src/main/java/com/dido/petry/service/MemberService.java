package com.dido.petry.service;

import com.dido.petry.dto.RegisterDTO;
import com.dido.petry.entity.Member;
import com.dido.petry.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final PasswordEncoder encoder;
    private final MemberRepository repository;

    @Transactional
    public void save(RegisterDTO dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        log.info("encoded={}", encoder.encode(dto.getPassword()));
        repository.save(dto.toEntity());
    }

    @Transactional
    public Member findUsername(String name, String email) {
        return repository.findByNameAndEmail(name, email);
    }

    @Transactional
    public boolean findPassword(String username, String email) {
        Member member = repository.findByUsernameAndEmail(username, email);
        if (member != null) {
            return true;
        } else {
            return false;
        }
    }
}
