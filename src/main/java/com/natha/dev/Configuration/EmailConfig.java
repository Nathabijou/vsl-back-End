package com.natha.dev.Configuration;


import com.natha.dev.Exeption.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Value;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
@Component
@Configuration
public class EmailConfig {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            emailSender.send(message);

        } catch (MessagingException | jakarta.mail.MessagingException e) {
            // Gérer les erreurs d'envoi d'e-mail
            e.printStackTrace();
        }
    }
}