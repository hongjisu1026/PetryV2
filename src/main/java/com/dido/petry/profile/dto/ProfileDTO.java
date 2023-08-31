package com.dido.petry.profile.dto;

import com.dido.petry.profile.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
@NoArgsConstructor
public class ProfileDTO {
    @NotBlank(message = "이름을 입력해주세요.")
    private String profileName;

    @Pattern(regexp = "^\\d{4}\\d{2}\\d{2}$", message = "생년월일은 8자리로 입력해주세요.")
    @NotBlank(message = "생년월일을 입력해주세요.")
    private String profileBirth;

    @NotBlank(message = "성별을 선택해주세요.")
    private String profileSex;

    @Transient
    public Profile toEntity() {
        Profile profile = Profile.builder()
                .profileName(this.profileName)
                .profileBirth(this.profileBirth)
                .profileSex(this.profileSex)
                .build();

        return profile;
    }
}
