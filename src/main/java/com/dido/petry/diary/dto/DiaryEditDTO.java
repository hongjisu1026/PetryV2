package com.dido.petry.diary.dto;

import com.dido.petry.diary.entity.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.Transient;

@Getter @Setter
@NoArgsConstructor
public class DiaryEditDTO {
    private String diaryTitle;
    private String diaryContent;

    @Transient
    public Diary toEntity() {
        Diary diary = Diary.builder()
                .diaryTitle(this.diaryTitle)
                .diaryContent(this.diaryContent)
                .build();

        return diary;
    }
}
