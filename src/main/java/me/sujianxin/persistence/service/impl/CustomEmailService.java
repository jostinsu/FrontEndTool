package me.sujianxin.persistence.service.impl;

import com.google.common.base.Strings;
import me.sujianxin.persistence.service.ICustomEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/12
 * <p>Time: 14:44
 * <p>Version: 1.0
 */
@Service
public class CustomEmailService implements ICustomEmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MimeMessage mimeMessage;

    @Async
    @Override
    public void send(String toAddress, String subject, String content) {
        //mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setTo(toAddress);
            if (!Strings.isNullOrEmpty(subject))
                mimeMessageHelper.setSubject(subject);
            if (!Strings.isNullOrEmpty(content))
                mimeMessageHelper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException ex) {
            ex.printStackTrace();
            try {
                Thread.sleep(1000 * 5);//5s后重发一次
                javaMailSender.send(mimeMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
