package me.sujianxin.persistence.service;

import me.sujianxin.persistence.model.FeElement;
import me.sujianxin.persistence.model.FeType;
import me.sujianxin.spring.config.ApplicationConfig;
import me.sujianxin.spring.config.PersistenceJPAConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/13
 * <p>Time: 12:28
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, PersistenceJPAConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class FeElementServiceTest {
    @Autowired
    private IFeElementService iFeElementService;

    @Test
    public void save() {
        FeElement feElement = new FeElement();
        feElement.setCode("code");
        feElement.setIcon("icon");
        feElement.setName("name");
        FeType feType = new FeType(1);

        feElement.setType(feType);
        iFeElementService.save(feElement);
    }
}
