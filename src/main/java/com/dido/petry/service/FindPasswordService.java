package com.dido.petry.service;

import com.dido.petry.dto.FindPasswordMailDTO;
import com.dido.petry.entity.Member;
import com.dido.petry.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class FindPasswordService {
    private final MemberRepository repository;
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public FindPasswordService(MemberRepository repository, SpringTemplateEngine templateEngine, JavaMailSender mailSender) {
        this.repository = repository;
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
    }

    public FindPasswordMailDTO createMailAndChangePassword(String username, String email, String tempPassword) {
        FindPasswordMailDTO dto = new FindPasswordMailDTO();
        dto.setAddress(email);
        dto.setTitle("[petry] 임시 비밀번호 안내");
        updatePassword(tempPassword, username, email);

        return dto;
    }

    public void updatePassword(String tempPassword, String username, String email) {
        Member member = repository.findByUsernameAndEmail(username, email);
        member.updatePassword(tempPassword);
    }

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
