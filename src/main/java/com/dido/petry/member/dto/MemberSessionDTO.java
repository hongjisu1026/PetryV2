package com.dido.petry.member.dto;

import com.dido.petry.member.entity.Member;
import com.dido.petry.member.entity.MemberRole;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class MemberSessionDTO implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String birth;
    private String nickname;
    private MemberRole role;

    public MemberSessionDTO(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.name = member.getName();
        this.email = member.getEmail();
        this.birth = member.getBirth();
        this.nickname = member.getNickname();
        this.role = member.getRole();
    }

    public Member toEntity() {
        Member member = Member.builder()
                .username(this.username)
                .password(this.password)
                .name(this.name)
                .email(this.email)
                .birth(this.birth)
                .nickname(this.nickname)
                .role(this.role)
                .build();
        return member;
    }
}
