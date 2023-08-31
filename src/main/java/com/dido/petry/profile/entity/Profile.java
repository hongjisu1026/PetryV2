package com.dido.petry.profile.entity;

import com.dido.petry.commonEntity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Profile extends BaseTimeEntity {
    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String profileName;

    @Column(nullable = false)
    private String profileBirth;

    @Column(nullable = false)
    private String profileSex;

    @Builder
    public Profile(String profileName, String profileBirth, String profileSex) {
        this.profileName = profileName;
        this.profileBirth = profileBirth;
        this.profileSex = profileSex;
    }
}
