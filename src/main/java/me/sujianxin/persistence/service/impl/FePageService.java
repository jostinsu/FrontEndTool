package me.sujianxin.persistence.service.impl;

import me.sujianxin.persistence.model.FePage;
import me.sujianxin.persistence.service.IFePageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/5
 * <p>Time: 23:04
 * <p>Version: 1.0
 */
@Service
public class FePageService implements IFePageService {
    @Override
    public void save(FePage fePage) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void updateById(FePage fePage) {

    }

    @Override
    public FePage findOne(int id) {
        return null;
    }

    @Override
    public Page<FePage> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
