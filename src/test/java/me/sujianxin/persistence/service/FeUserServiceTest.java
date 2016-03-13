package me.sujianxin.persistence.service;

import me.sujianxin.persistence.model.FeUser;
import me.sujianxin.spring.config.ApplicationConfig;
import me.sujianxin.spring.config.PersistenceJPAConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/7
 * <p>Time: 13:47
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, PersistenceJPAConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class FeUserServiceTest {
    @Autowired
    private IFeUserService feUserService;

    @Test
    public void updateNickname() {
        feUserService.updateNickname(1, "name");
    }

    @Test
    public void findOne() {
        FeUser feUser = feUserService.findOne(1);
        System.out.println(feUser);
    }

    @Test
    public void updatePassword() {
        Page<FeUser> tmp = feUserService.findBySpecification("%%", "%123%");
        System.out.println(tmp.getContent().size());
    }

    @Test
    public void existMail() {
        System.out.println(feUserService.existMail("75161201@qq.com"));
    }
}
