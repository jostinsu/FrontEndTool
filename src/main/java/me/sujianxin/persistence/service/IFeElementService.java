package me.sujianxin.persistence.service;

import me.sujianxin.persistence.model.FeElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/5
 * <p>Time: 23:02
 * <p>Version: 1.0
 */
public interface IFeElementService {
    void save(FeElement feElement);

    void deleteById(int id);

    void updateById(FeElement feElement);

    FeElement findOne(int id);

    Page<FeElement> findAll(Pageable pageable);

    List<FeElement> findByTypeId(int typeid);

    long count();
}
