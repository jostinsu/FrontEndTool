package me.sujianxin.persistence.service.impl;

import me.sujianxin.persistence.model.FeStyle;
import me.sujianxin.persistence.service.IFeStyleService;
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
public class FeStyleService implements IFeStyleService {
    @Override
    public void save(FeStyle feStyle) {

    }

    @Override
    public void deleteById() {

    }

    @Override
    public void updateById(FeStyle feStyle) {

    }

    @Override
    public FeStyle findOne(int id) {
        return null;
    }

    @Override
    public Page<FeStyle> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
