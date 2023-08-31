package com.dido.petry.profile.service;

import com.dido.petry.profile.dto.ProfileImageDTO;
import com.dido.petry.profile.entity.ProfileImage;
import com.dido.petry.profile.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileImageService {
    @Value("${resource.path}")
    private String uploadDir;
    private final ProfileImageRepository repository;

    @Transactional
    public void saveProfileImage(MultipartFile file, Long profileId) {
        try {
            String oriFileName = file.getOriginalFilename();
            String extension = getExtension(oriFileName);
            String fileName = getUUIDImageName() + extension;
            String filePath = uploadDir + File.separator + "profile" + File.separator + fileName;
            Long fileSize = file.getSize();

            File saveFile = new File(filePath);
            file.transferTo(saveFile);

            ProfileImageDTO dto = new ProfileImageDTO(fileName, oriFileName,filePath, fileSize, profileId);
            ProfileImage profileImage = saveProfileImage(dto);
            repository.save(profileImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProfileImage saveProfileImage(ProfileImageDTO dto) {
        return ProfileImage.builder()
                .profileImageName(dto.getProfileImageName())
                .profileOriImageName(dto.getProfileOriImageName())
                .profileImageURL(dto.getProfileImageURL())
                .profileImageSize(dto.getProfileImageSize())
                .profileId(dto.getProfileId())
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
}
