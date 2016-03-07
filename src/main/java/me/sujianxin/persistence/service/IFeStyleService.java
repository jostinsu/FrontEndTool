package me.sujianxin.persistence.service;

import me.sujianxin.persistence.model.FeStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/5
 * <p>Time: 23:03
 * <p>Version: 1.0
 */
public interface IFeStyleService {
    void save(FeStyle feStyle);

    void deleteById();

    void updateById(FeStyle feStyle);

    FeStyle findOne(int id);

    Page<FeStyle> findAll(Pageable pageable);

    long count();
}
