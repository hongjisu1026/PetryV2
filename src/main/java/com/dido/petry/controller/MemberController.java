package com.dido.petry.controller;

import com.dido.petry.dto.RegisterDTO;
import com.dido.petry.entity.Member;
import com.dido.petry.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {
    private final MemberService service;
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @PostMapping("/register-process")
    public String register(@ModelAttribute RegisterDTO dto) {
        log.info("pwd={}", dto.getPassword());
        service.save(dto);

        return "redirect:/login";
    }

    @GetMapping("/login-fail")
    public String loginFail(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        log.info("error={}, {}", error, exception);
        return "petry/login/login";
    }

    @PostMapping("/findUsername")
    public String findUsername(String iName, String iEmail, Model model) {
        Member member = service.findUsername(iName, iEmail);
        String username;
        boolean result;

        if (member == null) {
            username = null;
            result = false;
        } else {
            username = member.getUsername();
            result = true;
        }

        log.info("{}", result);

        model.addAttribute("username", username);
        model.addAttribute("type", "아이디");
        model.addAttribute("result", result);

        return "petry/login/login";
    }

    @GetMapping("/findPassword")
    public @ResponseBody Map<String, Boolean> findPassword(String username, String email) {
        Map<String, Boolean> data = new HashMap<>();
        boolean check = service.findPassword(username, email);
        data.put("check", check);

        return data;
    }

    @PostMapping("/findPassword")
    public void findPasswordSendEmail(String username, String email) {

    }
}
