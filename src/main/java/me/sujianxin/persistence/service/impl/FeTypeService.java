package me.sujianxin.persistence.service.impl;

import me.sujianxin.persistence.model.FeType;
import me.sujianxin.persistence.repository.FeTypeRepository;
import me.sujianxin.persistence.service.IFeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/5
 * <p>Time: 23:05
 * <p>Version: 1.0
 */
@Service
@Transactional
public class FeTypeService implements IFeTypeService {
    @Autowired
    private FeTypeRepository feTypeRepository;

    @Override
    public void save(FeType feType) {
        feTypeRepository.save(feType);
    }

    @Override
    public void deleteById(int id) {
        feTypeRepository.delete(id);
    }

    @Override
    public void updateById(FeType feType) {
        FeType tmp = feTypeRepository.findOne(feType.getId());
        if (null != tmp) {
            tmp.setName(feType.getName());
            feTypeRepository.save(tmp);
        }
    }

    @Override
    public FeType findOne(int id) {
        return feTypeRepository.findOne(id);
    }

    @Override
    public List<FeType> findAll() {
        return feTypeRepository.findAll();
    }

    @Override
    public long count() {
        return feTypeRepository.count();
    }
}
