package com.dido.petry.diary.dto;

import com.dido.petry.diary.entity.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.beans.Transient;

@Getter @Setter
@NoArgsConstructor
public class DiaryFormDTO {
    private Long id;
    @NotBlank(message = "제목을 입력해주세요.")
    private String diaryTitle;

    @NotBlank(message = "내용을 입력해주세요.")
    private String diaryContent;

    private Long memberId;

    @Transient
    public Diary toEntity() {
        Diary diary = Diary.builder()
                .diaryTitle(this.diaryTitle)
                .diaryContent(this.diaryContent)
                .build();

        return diary;
    }
}
