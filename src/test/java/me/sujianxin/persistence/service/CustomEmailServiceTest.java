package me.sujianxin.persistence.service;

import me.sujianxin.spring.config.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/12
 * <p>Time: 14:45
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = AnnotationConfigContextLoader.class)
@Rollback(false)
public class CustomEmailServiceTest {
    @Autowired
    private ICustomEmailService iCustomEmailService;

    @Test
    public void sendMail() {
        iCustomEmailService.send("751611201@qq.com", "subject 1", "hello laudukang");
    }

}
