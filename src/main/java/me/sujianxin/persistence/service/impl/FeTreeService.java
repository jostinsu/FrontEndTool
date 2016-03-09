package me.sujianxin.persistence.service.impl;

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
@Service("feTreeService")
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
    public FeTree findOne(int id) {
        return feTreeRepository.findOne(id);
    }

    @Override
    public List<FeTree> findAll() {
        return feTreeRepository.findAll();
    }

    @Override
    public long count() {
        return feTreeRepository.count();
    }
}
