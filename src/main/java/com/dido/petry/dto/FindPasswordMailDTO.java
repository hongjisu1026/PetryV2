package com.dido.petry.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FindPasswordMailDTO {
    private String address;
    private String title;
    private String message;
}
