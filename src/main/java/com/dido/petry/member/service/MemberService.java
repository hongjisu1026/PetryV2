package com.dido.petry.member.service;

import com.dido.petry.member.dto.MemberSessionDTO;
import com.dido.petry.member.dto.ModifyDTO;
import com.dido.petry.member.dto.RegisterDTO;
import com.dido.petry.member.entity.Member;
import com.dido.petry.member.repository.MemberRepository;
import com.dido.petry.member.repository.ValidationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final PasswordEncoder encoder;
    private final MemberRepository repository;
    private final ValidationRepository validationRepository;

    @Transactional
    public void save(RegisterDTO dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        repository.save(dto.toEntity());
    }

    @Transactional
    public String matchPassword(MemberSessionDTO dto, String password) {
        Member member = repository.findByUsername(dto.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 계정정보입니다."));

        log.info("password = {}", member.getPassword());

        boolean result = encoder.matches(password, member.getPassword());
        if (result) {
            return "ok";
        }

        return null;
    }

    @Transactional
    public String emailValidation(String email) {
        Member member = validationRepository.findByEmail(email);

        if (member == null) return null;

        return "exist";
    }

    @Transactional
    public String usernameValidation(String username) {
        Member member = validationRepository.findByUsername(username);

        if (member == null) return null;

        return "exist";
    }

    @Transactional
    public void delete(MemberSessionDTO dto) {
        repository.deleteByUsername(dto.getUsername());
        SecurityContextHolder.clearContext();
    }

    @Transactional
    public void modify(ModifyDTO dto) {
        Member member = repository.findByUsername(dto.getUsername()).orElseThrow(() ->
            new IllegalArgumentException("존재하지 않는 계정정보입니다."));

        if (dto.getPassword() != null && !dto.getPassword().equals("")) {
            String newPassword = encoder.encode(dto.getPassword());
            member.updatePassword(newPassword);
        }

        if (dto.getName() != null && !dto.getName().equals("")) member.updateName(dto.getName());

        if (dto.getNickname() != null && !dto.getNickname().equals("")) member.updateNickname(dto.getNickname());
    }


}
