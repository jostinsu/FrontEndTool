package me.sujianxin.persistence.service.impl;

import me.sujianxin.persistence.model.FeTree;
import me.sujianxin.persistence.service.IFeTreeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    @Override
    public void save(FeTree feTree) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void updateById(FeTree feTree) {

    }

    @Override
    public FeTree findOne(int id) {
        return null;
    }

    @Override
    public Page<FeTree> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
