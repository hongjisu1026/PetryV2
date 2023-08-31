package com.dido.petry.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    USER("USER"), ADMIN("ADMIN");
    private final String name;
}
