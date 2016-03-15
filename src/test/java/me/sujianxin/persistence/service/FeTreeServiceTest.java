package me.sujianxin.persistence.service;

import me.sujianxin.persistence.model.FeTree;
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
import java.io.File;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/15
 * <p>Time: 14:55
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, PersistenceJPAConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class FeTreeServiceTest {
    @Autowired
    private IFeTreeService iFeTreeService;

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

    @Test
    public void findOneByProjectId() {
        FeTree feTree = iFeTreeService.findOneByProjectId(1);
    }
}
