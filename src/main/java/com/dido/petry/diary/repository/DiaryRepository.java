package com.dido.petry.diary.repository;

import com.dido.petry.diary.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Page<Diary> findAllByMemberIdOrderByIdDesc(Pageable pageable, Long userId);
    Optional<Diary> findById(Long diaryId);

    void deleteById(Long diaryId);
}
