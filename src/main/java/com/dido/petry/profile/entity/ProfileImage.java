package com.dido.petry.profile.entity;

import com.dido.petry.commonEntity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProfileImage extends BaseTimeEntity {
    @Id
    @Column(name = "profile_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profileImageName;
    private String profileOriImageName;
    private String profileImageURL;
    private Long profileImageSize;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Builder
    public ProfileImage(String profileImageName, String profileOriImageName, String profileImageURL, Long profileImageSize, Long profileId) {
        this.profileImageName = profileImageName;
        this.profileOriImageName = profileOriImageName;
        this.profileImageURL = profileImageURL;
        this.profileImageSize = profileImageSize;
        this.profileId = profileId;
    }
}
