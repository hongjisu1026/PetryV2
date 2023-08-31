package com.dido.petry.profile.controller;

import com.dido.petry.profile.dto.ProfileDTO;
import com.dido.petry.profile.service.ProfileImageService;
import com.dido.petry.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService service;
    private final ProfileImageService imageService;

    @GetMapping("/profileList")
    public String profileListPage() {
        return "petry/setting/profileList";
    }

    @GetMapping("/profile")
    public String profileFormPage(Model model) {
        model.addAttribute("dto", new ProfileDTO());
        return "petry/setting/profile";
    }

    @PostMapping("/profile")
    public String addProfile(@Valid @ModelAttribute(value = "dto") ProfileDTO dto, BindingResult result, @RequestPart("profileImage") MultipartFile file, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("dto", dto);
            return "petry/setting/profile";
        }

        if (file.isEmpty()) return "redirect:/profile";

        Long profileId = service.saveProfile(dto);
        imageService.saveProfileImage(file, profileId);

        return "redirect:/profile/profileList";
    }
}
