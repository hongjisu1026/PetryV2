package com.dido.petry.member.dto;

import com.dido.petry.member.entity.Member;
import com.dido.petry.member.entity.MemberRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.beans.Transient;

@Getter @Setter
@NoArgsConstructor
public class RegisterDTO {
    @Size(min = 6, message = "아이디는 6자 이상 입력해주세요.")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @Pattern(regexp = "(^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$)", message = "비밀번호는 8~16자 영문자, 숫자 및 특수문자가 포함되어야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "유효하지않은 이메일 형식입니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Pattern(regexp = "^\\d{4}\\d{2}\\d{2}$", message = "생년월일은 8자리로 입력해주세요.")
    @NotBlank(message = "생년월일을 입력해주세요.")
    private String birth;

    @NotBlank(message = "닉네임을 입력해주세요.")
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
