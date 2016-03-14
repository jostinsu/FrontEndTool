package me.sujianxin.persistence.service.impl;

import me.sujianxin.persistence.model.FeStyle;
import me.sujianxin.persistence.repository.FeStyleRepository;
import me.sujianxin.persistence.service.IFeStyleService;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service
@Transactional
public class FeStyleService implements IFeStyleService {
    @Autowired
    private FeStyleRepository feStyleRepository;

    @Override
    public void save(FeStyle feStyle) {
        feStyleRepository.save(feStyle);
    }

    @Override
    public void deleteById(int id) {
        feStyleRepository.delete(id);
    }

    @Override
    public int updateById(int id, String code) {
        return feStyleRepository.updateById(id, code);
    }

    @Override
    public FeStyle findOne(int id) {
        return feStyleRepository.findOne(id);
    }

    @Override
    public Page<FeStyle> findAll(Pageable pageable) {
        return feStyleRepository.findAll(pageable);
    }

    @Override
    public long count() {
        return feStyleRepository.count();
    }
}
