package com.dido.petry.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    USER("USER"), ADMIN("ADMIN");
    private final String name;
}
