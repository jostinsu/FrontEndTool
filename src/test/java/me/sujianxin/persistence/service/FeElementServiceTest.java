package me.sujianxin.persistence.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sujianxin.persistence.model.FeElement;
import me.sujianxin.persistence.model.FeType;
import me.sujianxin.spring.config.ApplicationConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/13
 * <p>Time: 12:28
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class FeElementServiceTest {
    @Autowired
    private IFeElementService iFeElementService;

    private ObjectMapper objectMapper = null;

    @Before
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @After
    public void end() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findListByTypeId() {
        List<FeElement> feElementList = iFeElementService.findByTypeId(1);
        try {
            objectMapper.writeValue(System.out, feElementList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
