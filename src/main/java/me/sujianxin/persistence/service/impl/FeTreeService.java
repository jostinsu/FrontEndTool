package me.sujianxin.persistence.service.impl;

import me.sujianxin.persistence.model.FePage;
import me.sujianxin.persistence.model.FeTree;
import me.sujianxin.persistence.repository.FeTreeRepository;
import me.sujianxin.persistence.service.IFeTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/5
 * <p>Time: 23:04
 * <p>Version: 1.0
 */
@Service
@Transactional
public class FeTreeService implements IFeTreeService {
    @Autowired
    private FeTreeRepository feTreeRepository;

    @Override
    public void save(FeTree feTree) {
        feTreeRepository.save(feTree);
    }

    @Override
    public void deleteById(int id) {
        feTreeRepository.delete(id);
    }

    @Override
    public void updateById(FeTree feTree) {
        FeTree tmp = feTreeRepository.findOne(feTree.getId());
        if (null != tmp) {
            tmp.setLayer(feTree.getLayer());
            tmp.setTree(feTree.getTree());
            tmp.setName(feTree.getName());
            tmp.setIsFolder(feTree.getIsFolder());
            feTreeRepository.save(tmp);
        }
    }

    @Override
    public int updateNameById(int id, String name) {
        return feTreeRepository.updateNameById(id, name);
    }

    @Override
    public int updatePageByTreeId(int id, String code) {
        FeTree feTree = feTreeRepository.findOne(id);
        if (null != feTree && feTree.getPages().size() > 0) {
            FePage fePage = feTree.getPages().get(0);
            fePage.setCode(code);
            feTreeRepository.save(feTree);
            return 1;
        }
        return 0;
    }

    @Override
    public FeTree findOne(int id) {
        return feTreeRepository.findOne(id);
    }

    @Override
    public FeTree findPageByTreeId(int id) {
        return feTreeRepository.findPageByTreeId(id);
    }

//    @Override
//    public FeTree findOneByProjectId(int projectid) {
//        return feTreeRepository.findOneByProjectId(projectid);
//    }

    @Override
    public List<FeTree> findAll() {
        return feTreeRepository.findAll();
    }

    @Override
    public long count() {
        return feTreeRepository.count();
    }

    @Override
    public List<FeTree> findAllByProjectId(int id) {
        return null;
    }
}
