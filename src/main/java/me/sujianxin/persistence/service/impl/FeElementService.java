package me.sujianxin.persistence.service.impl;

import me.sujianxin.persistence.model.FeElement;
import me.sujianxin.persistence.repository.FeElementRepository;
import me.sujianxin.persistence.service.IFeElementService;
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
@Service("feElementService")
@Transactional
public class FeElementService implements IFeElementService {
    @Autowired
    private FeElementRepository feElementRepository;

    @Override
    public void save(FeElement feElement) {
        feElementRepository.save(feElement);
    }

    @Override
    public void deleteById(int id) {
        feElementRepository.delete(id);
    }

    @Override
    public void updateById(FeElement feElement) {
        FeElement tmp = feElementRepository.findOne(feElement.getId());
        if (null != tmp) {
            tmp.setType(feElement.getType());
            tmp.setCode(feElement.getCode());
            tmp.setRemark(feElement.getRemark());
            tmp.setName(feElement.getName());
            tmp.setIcon(feElement.getIcon());
            feElementRepository.save(tmp);
        }
    }

    @Override
    public FeElement findOne(int id) {
        return feElementRepository.findOne(id);
    }

    @Override
    public Page<FeElement> findAll(Pageable pageable) {
        return feElementRepository.findAll(pageable);
    }

    @Override
    public long count() {
        return feElementRepository.count();
    }
}
