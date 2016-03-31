package me.sujianxin.persistence.service;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.sujianxin.persistence.model.FePage;
import me.sujianxin.persistence.model.FeTree;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/15
 * <p>Time: 14:55
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
//@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
public class FeTreeServiceTest {
    @Autowired
    private IFeTreeService iFeTreeService;
    private JsonGenerator jsonGenerator = null;
    private ObjectMapper objectMapper = null;

    public static void printTree(FeTree feTree, String sb) {
        if (feTree.getIsFolder().equals("1")) {
            sb = sb + feTree.getName() + File.separator;
        } else {
            System.out.println("path=" + sb + feTree.getName());
        }
        if (feTree.getTrees().size() > 0) {
            for (FeTree tree : feTree.getTrees()) {
                printTree(tree, sb);
            }
        }
    }

    @Before
    public void init() {
        objectMapper = new ObjectMapper();
        try {
            jsonGenerator = objectMapper.getFactory().createGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void findOneByProjectId() {
        FeTree feTree = iFeTreeService.findOne(1);
    }

    @Test
    public void findPageByTreeId() {
        FeTree tree = iFeTreeService.findOne(5);
        try {
            jsonGenerator.writeObject(tree);
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(tree);
    }

    @Test
    public void save() {
        FeTree feTree = new FeTree();
        feTree.setName("tree_name");
        feTree.setIsFolder("0");
        feTree.setIconSkin("page");

        FePage fePage = new FePage();
        fePage.setDownloadCode("code");
        feTree.addPage(fePage);

        iFeTreeService.save(feTree);
    }

    @Test
    public void updatePageByTreeId() {
        System.out.println(iFeTreeService.updatePageByTreeId(4, "what the fuck"));
    }
}
