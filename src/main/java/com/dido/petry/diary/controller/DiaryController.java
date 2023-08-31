package com.dido.petry.diary.controller;

import com.dido.petry.diary.dto.DiaryEditDTO;
import com.dido.petry.diary.dto.DiaryFormDTO;
import com.dido.petry.diary.entity.Diary;
import com.dido.petry.diary.entity.DiaryImage;
import com.dido.petry.diary.service.DiaryImageService;
import com.dido.petry.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService service;
    private final DiaryImageService imageService;

    @GetMapping("/diaryList")
    public String diaryPage(Model model, @PageableDefault Pageable pageable) {
        Page<Diary> diaries = service.selectDiaryList(pageable);
        model.addAttribute("diaryList", diaries);

        model.addAttribute("diaryImage", imageService.getThumbnailImage());

        int nowPage = diaries.getPageable().getPageNumber() + 1;
        int endPage = diaries.getTotalPages();

        List<Integer> page = new ArrayList<>();
        for (int i = 0; i < endPage; i++) {
            page.add(i);
        }

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("page", page);

        return "petry/diary/diaryList";
    }

    @GetMapping("/diary")
    public String diaryFormPage(Model model) {
        model.addAttribute("dto", new DiaryFormDTO());
        return "petry/diary/diary";
    }

    @PostMapping("/diary")
    public String addDiary(@Valid @ModelAttribute(value = "dto") DiaryFormDTO dto, BindingResult result, @RequestPart("diaryImage") MultipartFile[] files, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("dto", dto);
            return "petry/diary/diary";
        }
        Long diaryId = service.saveDiary(dto);

        if (files != null) {
            imageService.saveDiaryImage(files, diaryId);
        }

        log.info("file = {}", files.length);

        return "redirect:/diary/diaryList";
    }

    @GetMapping("/detail/{id}")
    public String selectDiaryDetail(@PathVariable Long id, Model model) {
        Diary diary = service.selectDiary(id);
        List<DiaryImage> images = imageService.selectDiaryImages(id);

        model.addAttribute("diary", diary);
        model.addAttribute("diaryImage", images);

        return "petry/diary/diaryDetail";
    }

    @GetMapping("/edit/{id}")
    public String editDiaryForm(@PathVariable Long id, Model model) {
        Diary diary = service.selectDiary(id);
        model.addAttribute("diary", diary);

        return "petry/diary/editDiary";
    }

    @PostMapping("/edit/{id}")
    public String editDiary(@PathVariable Long id, @ModelAttribute DiaryEditDTO dto) {
        Diary diary = service.selectDiary(id);
        service.editDiary(diary, dto);

        return "redirect:/diary/diaryList";
    }

    @GetMapping("/delete/{id}")
    public String deleteDiary(@PathVariable Long id) {
        service.deleteDiary(id);
        imageService.deleteDiaryImage(id);

        return "redirect:/diary/diaryList";
    }
}
