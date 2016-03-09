package me.sujianxin.spring.events;

import org.springframework.context.ApplicationEvent;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/12
 * <p>Time: 1:28
 * <p>Version: 1.0
 */
public class EmailEvent extends ApplicationEvent {
    private String toAddress;
    private String subject;
    private String content;

    public EmailEvent(Object source) {
        super(source);
    }

    public EmailEvent(Object source, String toAddress, String subject, String content) {
        super(source);
        this.toAddress = toAddress;
        this.subject = subject;
        this.content = content;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
