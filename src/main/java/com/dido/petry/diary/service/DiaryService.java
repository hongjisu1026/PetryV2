package com.dido.petry.diary.service;

import com.dido.petry.diary.dto.DiaryEditDTO;
import com.dido.petry.diary.dto.DiaryFormDTO;
import com.dido.petry.diary.entity.Diary;
import com.dido.petry.diary.repository.DiaryRepository;
import com.dido.petry.member.dto.MemberSessionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository repository;
    private final HttpSession session;

    @Transactional
    public Page<Diary> selectDiaryList(Pageable pageable) {
        MemberSessionDTO dto = (MemberSessionDTO) session.getAttribute("member");
        Long id = dto.getId();

        log.info("id={}", id);

        return repository.findAllByMemberIdOrderByIdDesc(pageable, id);
    }

    @Transactional
    public Long saveDiary(DiaryFormDTO dto) {
        MemberSessionDTO sessionDTO = (MemberSessionDTO) session.getAttribute("member");
        Long memberId = sessionDTO.getId();

        Diary diary = Diary.builder()
                .diaryTitle(dto.getDiaryTitle())
                .diaryContent(dto.getDiaryContent())
                .memberId(memberId)
                .build();

        return repository.save(diary).getId();
    }

    @Transactional
    public Diary selectDiary(Long diaryId) {
        Diary diary = repository.findById(diaryId).orElseThrow(() ->
                new IllegalArgumentException("존재하지않습니다."));

        return diary;
    }

    @Transactional
    public void editDiary(Diary diary, DiaryEditDTO dto) {
        diary.updateDiary(dto.getDiaryTitle(), dto.getDiaryContent());
    }

    @Transactional
    public void deleteDiary(Long id) {
        repository.deleteById(id);
    }
}
