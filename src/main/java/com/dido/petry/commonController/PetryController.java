package com.dido.petry.commonController;

import com.dido.petry.diary.dto.DiaryFormDTO;
import com.dido.petry.diary.entity.DiaryImage;
import com.dido.petry.diary.service.DiaryImageService;
import com.dido.petry.member.dto.MemberSessionDTO;
import com.dido.petry.member.dto.RegisterDTO;
import com.dido.petry.profile.dto.ProfileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class PetryController {
    private final HttpSession session;
    private final DiaryImageService imageService;

    @GetMapping
    public String mainPage(Model model) {
        List<DiaryImage> imageList = imageService.selectCurrentDiaryImage();
        if (!imageList.isEmpty()) model.addAttribute("currentImage", imageList);

        return "petry/main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "petry/login/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("dto", new RegisterDTO());
        return "petry/register/register";
    }

    @GetMapping("/album")
    public String albumPage() {
        return "petry/album/album";
    }

    @GetMapping("/setting")
    public String settingPage() {
        return "petry/setting/setting";
    }

    @GetMapping("/modify")
    public String modifyPage(Model model) {
        MemberSessionDTO dto = (MemberSessionDTO) session.getAttribute("member");
        model.addAttribute("member", dto);

        return "petry/setting/modify";
    }


}
