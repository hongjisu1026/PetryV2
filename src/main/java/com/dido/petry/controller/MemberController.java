package com.dido.petry.controller;

import com.dido.petry.dto.FindPasswordMailDTO;
import com.dido.petry.dto.FindUsernameDTO;
import com.dido.petry.dto.RegisterDTO;
import com.dido.petry.entity.Member;
import com.dido.petry.service.FindPasswordService;
import com.dido.petry.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {
    private final MemberService service;
    private final FindPasswordService findPasswordService;

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
    @ResponseBody
    public String findUsername(String name, String email) {
        String username = service.findUsername(name, email);

        return username;
    }

    @PostMapping("/findPassword")
    public @ResponseBody Map<String, Boolean> findPassword(String username, String email) {
        Map<String, Boolean> data = new HashMap<>();
        boolean check = service.findPassword(username, email);
        data.put("check", check);

        return data;
    }

    @PostMapping("/sendMail")
    public String findPasswordSendEmail(String username, String email) throws MessagingException {
        String tempPassword = findPasswordService.tempPassword();
        FindPasswordMailDTO dto = findPasswordService.createMailAndChangePassword(username, email, tempPassword);
        findPasswordService.mailSend(dto, username, tempPassword);

        return "redirect:/login";
    }
}
