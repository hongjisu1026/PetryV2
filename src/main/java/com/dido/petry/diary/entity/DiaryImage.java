package com.dido.petry.diary.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class DiaryImage {
    @Id
    @Column(name = "diary_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diaryImageName;
    private String diaryOriImageName;
    private String diaryImageURL;
    private String repImageYN;

    private Long diaryImageSize;

    @Column(name = "diary_id", nullable = false)
    private Long diaryId;

    @Builder
    public DiaryImage(String diaryImageName, String diaryOriImageName, String diaryImageURL, String repImageYN, Long diaryImageSize, Long diaryId) {
        this.diaryImageName = diaryImageName;
        this.diaryOriImageName = diaryOriImageName;
        this.diaryImageURL = diaryImageURL;
        this.repImageYN = repImageYN;
        this.diaryImageSize = diaryImageSize;
        this.diaryId = diaryId;
    }
}
