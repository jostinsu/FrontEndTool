package me.sujianxin.persistence.service;

import me.sujianxin.persistence.model.FeProject;
import me.sujianxin.persistence.model.FeUser;
import me.sujianxin.spring.config.ApplicationConfig;
import me.sujianxin.spring.config.PersistenceJPAConfig;
import me.sujianxin.spring.domain.FeProjectDomain;
import me.sujianxin.spring.domain.FeProjectForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: sujianxin
 * <p>Date: 2016/3/4
 * <p>Time: 19:49
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, PersistenceJPAConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class FeProjectServiceTest {
    @Autowired
    private IFeProjectService feProjectService;
    private SimpleDateFormat sdf;

    @Before
    public void init() {
        this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void findAllByPage() {
        FeProjectForm feProjectForm = new FeProjectForm();
        feProjectForm.setPage(1);
        feProjectForm.setPageSize(100);
        feProjectForm.setSortCol("createTime");
        feProjectForm.setSortDir("desc");
        // feProjectForm.setName("项目");
        Map<String, Object> result = feProjectService.findAll(feProjectForm, 1);
        System.out.println(((List<FeProjectDomain>) result.get("data")).size());
    }

    @Test
    public void save() {
        FeProject project = new FeProject();
        project.setName("name_" + sdf.format(new Date()));
        project.setCreateTime(new Date());
        project.setRemark("remark_" + sdf.format(new Date()));
        project.setUser(new FeUser(1));
        FeProject tmp = feProjectService.save(project);
        System.out.println(project.hashCode());
        System.out.println(tmp.hashCode());
    }

    @Test
    public void updateById() {
        FeProject project = new FeProject();
        project.setId(3);
        project.setName("update" + sdf.format(new Date()));
        project.setCreateTime(new Date());
        project.setRemark("update" + sdf.format(new Date()));
        project.setUser(new FeUser(1));
        feProjectService.save(project);
    }

    @Test
    public void deleteById() {
        feProjectService.deleteById(4);
    }
}
