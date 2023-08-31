package com.dido.petry.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImageDTO {
    private String profileImageName;
    private String profileOriImageName;
    private String profileImageURL;
    private Long profileImageSize;
    private Long profileId;
}
