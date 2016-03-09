package me.sujianxin.persistence.service;

import me.sujianxin.persistence.model.FeType;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/5
 * <p>Time: 23:03
 * <p>Version: 1.0
 */
public interface IFeTypeService {
    void save(FeType feType);

    void deleteById(int id);

    void updateById(FeType feType);

    FeType findOne(int id);

    List<FeType> findAll();

    long count();
}
