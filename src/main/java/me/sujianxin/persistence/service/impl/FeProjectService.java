package me.sujianxin.persistence.service.impl;

import me.sujianxin.persistence.model.FeProject;
import me.sujianxin.persistence.repository.FeProjectRepository;
import me.sujianxin.persistence.service.IFeProjectService;
import me.sujianxin.spring.domain.FeProjectForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: sujianxin
 * <p>Date: 2016/3/3
 * <p>Time: 22:32
 * <p>Version: 1.0
 */
@Service("feProjectService")
@Transactional
public class FeProjectService implements IFeProjectService {
    @Autowired
    private FeProjectRepository feProjectRepository;

    @Override
    public FeProject save(FeProject feProject) {
        return feProjectRepository.save(feProject);
    }

    @Override
    public void updateById(FeProject feProject) {
        FeProject tmp = feProjectRepository.findOne(feProject.getId());
        if (null != tmp) {
            tmp.setRemark(feProject.getRemark());
            tmp.setName(feProject.getName());
        }
        feProjectRepository.save(tmp);
    }

    @Override
    public void deleteById(int id) {
        feProjectRepository.delete(id);
    }


    @Override
    public Map<String, Object> findAll(FeProjectForm feProjectForm) {
        return feProjectRepository.findByPage(feProjectForm);
    }

    @Override
    public long count() {
        return feProjectRepository.count();
    }
}
