package com.personal.marketnote.user.service.authentication;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.RandomCodeGenerator;
import com.personal.marketnote.user.port.in.usecase.authentication.SendEmailVerificationUseCase;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@UseCase
@RequiredArgsConstructor
public class SendEmailVerificationService implements SendEmailVerificationUseCase {
    private final JavaMailSender mailSender;

    @Value("${mail.from:no-reply@example.com}")
    private String fromAddress;

    @Value("${mail.sender-name:shop}")
    private String senderName;

    @Value("${mail.verification.ttl-minutes:5}")
    private int ttlMinutes;

    @Override
    public void sendEmailVerification(String email) {
        String verificationCode = RandomCodeGenerator.generateEmailVerificationCode();
        sendVerificationEmail(email, verificationCode);
    }

    private void sendVerificationEmail(String to, String verificationCode) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false, StandardCharsets.UTF_8.name());
            helper.setTo(to);
            helper.setFrom(fromAddress, senderName);
            helper.setSubject("[마켓노트] 이메일 인증 코드");

            String htmlBody = """
                    <div style=\"font-family:Arial,Helvetica,sans-serif;font-size:14px;color:#222\"> 
                      <p>마켓노트 페이지에서 아래 인증 코드를 입력해 이메일 인증을 완료하세요.</p>
                      <p style=\"font-size:18px;font-weight:700;letter-spacing:2px\">인증 코드: %s</p>
                      <p>유효기간: %d분</p>
                      <p style=\"color:#666\">본 메일은 발신 전용입니다.</p>
                    </div>
                    """.formatted(verificationCode, ttlMinutes);

            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new MailSendException("Failed to send verification email", e);
        }
    }
}
