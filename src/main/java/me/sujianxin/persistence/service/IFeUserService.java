package me.sujianxin.persistence.service;

import me.sujianxin.persistence.model.FeUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/5
 * <p>Time: 23:04
 * <p>Version: 1.0
 */
public interface IFeUserService {
    void save(FeUser feUser);

    void deleteById(int id);

    int updateNickname(int id, String nickname);

    int updatePassword(int id, String password);

    FeUser findOne(int id);

    Object[] login(String name, String password);

    Page<FeUser> findAll(Pageable pageable);

    long count();
}
