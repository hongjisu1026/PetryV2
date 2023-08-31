package com.dido.petry.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyDTO {
    private String username;
    private String password;
    private String name;
    private String nickname;
}
