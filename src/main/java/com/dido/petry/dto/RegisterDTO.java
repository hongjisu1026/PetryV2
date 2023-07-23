package com.dido.petry.dto;

import com.dido.petry.entity.Member;
import com.dido.petry.role.MemberRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.Transient;

@Getter @Setter
@NoArgsConstructor
public class RegisterDTO {
    private String username;
    private String password;
    private String email;
    private String name;
    private String birth;
    private String nickname;
    private MemberRole role;

    @Transient
    public Member toEntity() {
        return Member.builder()
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .name(this.name)
                .birth(this.birth)
                .nickname(this.nickname)
                .role(role.USER)
                .build();
    }
}
