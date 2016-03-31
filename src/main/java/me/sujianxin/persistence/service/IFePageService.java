package me.sujianxin.persistence.service;

import me.sujianxin.persistence.model.FePage;
import me.sujianxin.spring.domain.FePageDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/5
 * <p>Time: 23:02
 * <p>Version: 1.0
 */
public interface IFePageService {
    void save(FePage fePage);

    void deleteById(int id);

    void updateById(FePageDomain fePageDomain);

    int updateCode(FePageDomain fePageDomain);

    FePage findOne(int id);

    Page<FePage> findAll(Pageable pageable);

    long count();
}
