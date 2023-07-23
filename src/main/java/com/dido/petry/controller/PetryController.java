package com.dido.petry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PetryController {
    @GetMapping
    public String mainPage() {
        return "petry/main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "petry/login/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "petry/register/register";
    }

    @GetMapping("/diaryList")
    public String diaryPage() {
        return "petry/diary/diaryList";
    }

    @GetMapping("/album")
    public String albumPage() {
        return "petry/album/album";
    }

    @GetMapping("/setting")
    public String settingPage() {
        return "petry/setting/setting";
    }
}
