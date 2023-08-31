package com.dido.petry.diary.dto;

import com.dido.petry.diary.entity.DiaryImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.Transient;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryImageDTO {
    private String diaryImageName;
    private String diaryOriImageName;
    private String diaryImageURL;
    private String repImgYN;
    private Long diaryImageSize;
    private Long diaryId;

    @Transient
    public DiaryImage toEntity() {
        DiaryImage image = DiaryImage.builder()
                .diaryImageName(this.diaryImageName)
                .diaryOriImageName(this.diaryOriImageName)
                .diaryImageURL(this.diaryImageURL)
                .repImageYN(this.repImgYN)
                .diaryImageSize(this.diaryImageSize)
                .diaryId(this.diaryId)
                .build();

        return image;
    }
}
