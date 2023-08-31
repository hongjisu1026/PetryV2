package com.dido.petry.member.controller;

import com.dido.petry.member.dto.FindPasswordMailDTO;
import com.dido.petry.member.dto.MemberSessionDTO;
import com.dido.petry.member.dto.ModifyDTO;
import com.dido.petry.member.dto.RegisterDTO;
import com.dido.petry.member.service.FindService;
import com.dido.petry.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {
    private final MemberService service;
    private final FindService findService;
    private final HttpSession session;

    @PostMapping("/register-process")
    public String register(@Valid @ModelAttribute(value = "dto") RegisterDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("dto", dto);
            return "petry/register/register";
        }

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

    @GetMapping("/findUsername")
    @ResponseBody
    public String findUsername(String name, String email) {
        String username = findService.findUsername(name, email);

        return username;
    }

    @PostMapping("/findPassword")
    @ResponseBody
    public boolean findPassword(String username, String email) {
        boolean check = findService.findPassword(username, email);

        return check;
    }

    @PostMapping("/sendMail")
    public String findPasswordSendEmail(String username, String email) throws MessagingException {
        String tempPassword = findService.tempPassword();
        FindPasswordMailDTO dto = findService.createMailAndChangePassword(email);
        findService.mailSend(dto, username, tempPassword);
        findService.updatePassword(tempPassword, username, email);

        return "redirect:/login";
    }

    @GetMapping("/matchPassword")
    @ResponseBody
    public String matchPassword(String password) {
        MemberSessionDTO dto = (MemberSessionDTO) session.getAttribute("member");

        return service.matchPassword(dto, password);
    }

    @PostMapping("/delete")
    public String delete() {
        MemberSessionDTO dto = (MemberSessionDTO) session.getAttribute("member");
        service.delete(dto);
        session.invalidate();

        log.info("session = {}", session.getAttribute("member"));

        return "redirect:/login";
    }



    @PostMapping("/modify")
    @ResponseBody
    public String modify(String username, String password, String name, String nickname) {
        ModifyDTO dto = new ModifyDTO(username, password, name, nickname);
        service.modify(dto);

        return "ok";
    }

    @PostMapping("/emailValidation")
    @ResponseBody
    public String emailValidation(String email) {
        log.info("email={}", service.emailValidation(email));
        return service.emailValidation(email);
    }

    @PostMapping("/usernameValidation")
    @ResponseBody
    public String usernameValidation(String username) {
        log.info("username={}", service.usernameValidation(username));
        return service.usernameValidation(username);
    }
}
