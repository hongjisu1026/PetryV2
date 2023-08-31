package com.dido.petry.diary.entity;

import com.dido.petry.commonEntity.BaseTimeEntity;
import com.dido.petry.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Diary extends BaseTimeEntity {
    @Id
    @Column(name = "diary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String diaryTitle;

    @Column(nullable = false)
    private String diaryContent;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Builder
    public Diary(String diaryTitle, String diaryContent, Long memberId) {
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
        this.memberId = memberId;
    }

    public void updateDiary(String diaryTitle, String diaryContent) {
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
    }
}
