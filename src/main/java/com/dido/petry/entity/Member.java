package com.dido.petry.entity;

import com.dido.petry.role.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;
    private String birth;
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @Builder
    public Member(String username, String password, String email, String name, String birth, String nickname, MemberRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.nickname = nickname;
        this.role = role;
    }
}
