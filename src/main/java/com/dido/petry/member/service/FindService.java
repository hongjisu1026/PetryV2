package com.dido.petry.member.service;

import com.dido.petry.member.dto.FindPasswordMailDTO;
import com.dido.petry.member.entity.Member;
import com.dido.petry.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class FindService {
    private final MemberRepository repository;
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final PasswordEncoder encoder;
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public FindService(MemberRepository repository, SpringTemplateEngine templateEngine, JavaMailSender mailSender, PasswordEncoder encoder) {
        this.repository = repository;
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
        this.encoder = encoder;
    }

    @Transactional
    public String findUsername(String name, String email) {
        Member member = repository.findByNameAndEmail(name, email).orElseThrow(() ->
                new IllegalArgumentException("존재하지않는 계정정보입니다."));
        String username = null;
        if (member != null) username = member.getUsername();
        return username;
    }

    @Transactional
    public boolean findPassword(String username, String email) {
        Member member = repository.findByUsernameAndEmail(username, email).orElseThrow(() ->
            new IllegalArgumentException("존재하지않는 계정정보입니다."));

        if (member != null) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public FindPasswordMailDTO createMailAndChangePassword(String email) {
        FindPasswordMailDTO dto = new FindPasswordMailDTO();
        dto.setAddress(email);
        dto.setTitle("[petry] 임시 비밀번호 안내");

        return dto;
    }

    @Transactional
    public void updatePassword(String tempPassword, String username, String email) {
        String encryptPassword = encoder.encode(tempPassword);
        Member member = repository.findByUsernameAndEmail(username, email).orElseThrow(() ->
                new IllegalArgumentException("존재하지않는 계정정보입니다."));

        member.updatePassword(encryptPassword);
    }

    @Transactional
    public String tempPassword() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        char[] chs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        for (int i = 0; i < 10; i++) {
            builder.append(chs[random.nextInt(chs.length)]);
        }

        return builder.toString();
    }

    @Transactional
    public void mailSend(FindPasswordMailDTO dto, String username, String tempPassword) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(dto.getAddress());
        helper.setFrom(from);
        helper.setSubject(dto.getTitle());

        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("tempPassword", tempPassword);

        String html = templateEngine.process("mail", context);
        helper.setText(html, true);

        helper.addInline("logo", new ClassPathResource("static/assets/logo.png"));

        mailSender.send(message);
    }
}
