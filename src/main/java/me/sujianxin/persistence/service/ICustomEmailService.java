package me.sujianxin.persistence.service;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/12
 * <p>Time: 14:44
 * <p>Version: 1.0
 */
public interface ICustomEmailService {
    void send(String toAddress, String subject, String content);
}
