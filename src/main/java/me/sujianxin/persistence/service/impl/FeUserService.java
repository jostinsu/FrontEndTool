package me.sujianxin.persistence.service.impl;

import me.sujianxin.persistence.model.FeUser;
import me.sujianxin.persistence.repository.FeUserRepository;
import me.sujianxin.persistence.service.IFeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/6
 * <p>Time: 23:35
 * <p>Version: 1.0
 */
@Service
public class FeUserService implements IFeUserService {
    @Autowired
    private FeUserRepository feUserRepository;

    @Override
    public void save(FeUser feUser) {
        feUserRepository.save(feUser);
    }

    @Override
    public void deleteById(int id) {
        feUserRepository.delete(id);
    }


    @Override
    public int updateNickname(int id, String nickname) {
        return feUserRepository.updateNickname(id, nickname);
    }

    @Override
    public int updatePassword(int id, String password) {
        return feUserRepository.updatePassword(id, password);

    }

    @Override
    public FeUser findOne(int id) {
        return feUserRepository.findOne(id);
    }

    @Override
    public Object[] login(String name, String password) {
        return feUserRepository.login(name, password);
    }

    @Override
    public Page<FeUser> findAll(Pageable pageable) {
        return feUserRepository.findAll(pageable);
    }

    @Override
    public long count() {
        return feUserRepository.count();
    }
}
