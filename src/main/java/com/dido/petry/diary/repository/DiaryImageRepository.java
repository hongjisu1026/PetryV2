package com.dido.petry.diary.repository;

import com.dido.petry.diary.entity.DiaryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
    List<DiaryImage> findAllByRepImageYN(String Yn);
    List<DiaryImage> findAllByDiaryId(Long diaryId);

    void deleteByDiaryId(Long diaryId);

    List<DiaryImage> findTop5ByOrderByIdDesc();
}
