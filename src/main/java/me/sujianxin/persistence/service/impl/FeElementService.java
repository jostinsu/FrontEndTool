package me.sujianxin.persistence.service.impl;

import me.sujianxin.persistence.model.FeElement;
import me.sujianxin.persistence.service.IFeElementService;
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
public class FeElementService implements IFeElementService {
    @Override
    public void save(FeElement feElement) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void updateById(FeElement feElement) {

    }

    @Override
    public FeElement findOne(int id) {
        return null;
    }

    @Override
    public Page<FeElement> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
