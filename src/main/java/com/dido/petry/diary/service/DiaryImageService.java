package com.dido.petry.diary.service;

import com.dido.petry.diary.dto.DiaryImageDTO;
import com.dido.petry.diary.entity.Diary;
import com.dido.petry.diary.entity.DiaryImage;
import com.dido.petry.diary.repository.DiaryImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryImageService {
    @Value("${resource.path}")
    private String uploadDir;

    private final DiaryImageRepository repository;

    @Transactional
    public void saveDiaryImage(MultipartFile[] files, Long diaryId) {
        int count = 0;

        try {
            for (MultipartFile file : files) {
                String oriFileName = file.getOriginalFilename();
                String extension = getExtension(oriFileName);
                String fileName = getUUIDImageName() + extension;
                String filePath = uploadDir + File.separator + "diary" + File.separator + fileName;
                String repImage = "N";
                if (++count == 1) repImage = "Y";
                Long fileSize = file.getSize();

                File saveFile = new File(filePath);
                file.transferTo(saveFile);

                DiaryImageDTO dto = new DiaryImageDTO(fileName, oriFileName, filePath, repImage, fileSize, diaryId);
                DiaryImage diaryImage = saveDiaryImage(dto);
                repository.save(diaryImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DiaryImage saveDiaryImage(DiaryImageDTO dto) {
        return DiaryImage.builder()
                .diaryImageName(dto.getDiaryImageName())
                .diaryOriImageName(dto.getDiaryOriImageName())
                .diaryImageURL(dto.getDiaryImageURL())
                .repImageYN(dto.getRepImgYN())
                .diaryImageSize(dto.getDiaryImageSize())
                .diaryId(dto.getDiaryId())
                .build();
    }

    public String getExtension(String oriFileName) {
        int index = oriFileName.lastIndexOf(".");
        return oriFileName.substring(index);
    }

    public String getUUIDImageName() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().split("-")[0];
    }

    @Transactional
    public List<DiaryImage> getThumbnailImage() {
        return repository.findAllByRepImageYN("Y");
    }

    @Transactional
    public List<DiaryImage> selectDiaryImages(Long diaryId) {
        return repository.findAllByDiaryId(diaryId);
    }

    @Transactional
    public void deleteDiaryImage(Long diaryId) {
        List<DiaryImage> images = repository.findAllByDiaryId(diaryId);
        log.info("diaryImages={}", images.size());
        for (DiaryImage image : images) {
            File file = new File(image.getDiaryImageURL());
            if (file.exists()) {
                if (file.delete()) {
                    repository.deleteByDiaryId(diaryId);
                    log.info("파일 삭제 성공");
                }
                else log.info("파일 삭제 중 오류발생");
            } else {
                log.info("파일이 존재하지 않음");
            }
        }
    }

    @Transactional
    public List<DiaryImage> selectCurrentDiaryImage() {
        return repository.findTop5ByOrderByIdDesc();
    }
}
