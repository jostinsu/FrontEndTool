package me.sujianxin.persistence.service.impl;

import me.sujianxin.persistence.model.FeType;
import me.sujianxin.persistence.service.IFeTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/5
 * <p>Time: 23:05
 * <p>Version: 1.0
 */
@Service("feTypeService")
@Transactional
public class FeTypeService implements IFeTypeService {
    @Override
    public void save(FeType feType) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void updateById(FeType feType) {

    }

    @Override
    public FeType findOne(int id) {
        return null;
    }

    @Override
    public Page<FeType> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
