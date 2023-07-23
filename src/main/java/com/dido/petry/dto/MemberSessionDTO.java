package com.dido.petry.dto;

import com.dido.petry.entity.Member;
import com.dido.petry.role.MemberRole;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class MemberSessionDTO implements Serializable {
    private String username;
    private String password;
    private String name;
    private String email;
    private String birth;
    private String nickname;
    private MemberRole role;

    public MemberSessionDTO(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.name = member.getName();
        this.email = member.getEmail();
        this.birth = member.getBirth();
        this.nickname = member.getNickname();
        this.role = member.getRole();
    }
}
