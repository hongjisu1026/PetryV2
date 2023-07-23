package com.dido.petry.service;

import com.dido.petry.dto.MemberSessionDTO;
import com.dido.petry.entity.Member;
import com.dido.petry.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository repository;
    private final HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = repository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("존재하지않는 아이디입니다."));

        session.setAttribute("member", new MemberSessionDTO(member));

        return new MemberDetails(member);
    }
}
