package me.sujianxin.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * <p>Created with IDEA
 * <p>Author: sujianxin
 * <p>Date: 2016/1/22
 * <p>Time: 16:25
 * <p>Version: 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableSpringDataWebSupport
@PropertySource({"classpath:system.properties"})
//@EnableWebMvc
public class ApplicationConfig {
    @Autowired
    private Environment environment;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(environment.getProperty("mail.host"));
        javaMailSender.setPort(Integer.valueOf(environment.getProperty("mail.port")));
        javaMailSender.setUsername(environment.getProperty("mail.username"));
        javaMailSender.setPassword(environment.getProperty("mail.password"));
        javaMailSender.setDefaultEncoding(environment.getProperty("mail.defaultEncoding"));
        javaMailSender.setProtocol(environment.getProperty("mail.protocol"));
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", environment.getProperty("mail.transport.protocol"));
        properties.put("mail.smtp.auth", environment.getProperty("mail.smtp.auth"));
        properties.put("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable"));
        properties.put("mail.debug", environment.getProperty("mail.debug"));
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() throws UnsupportedEncodingException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(environment.getProperty("mail.from"));
        simpleMailMessage.setSubject(environment.getProperty("mail.default.subject"));
        simpleMailMessage.setText(environment.getProperty("mail.default.text"));
        return simpleMailMessage;
    }

}
