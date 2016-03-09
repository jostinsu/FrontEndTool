package me.sujianxin.persistence.service;

import me.sujianxin.persistence.model.FeTree;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/5
 * <p>Time: 23:03
 * <p>Version: 1.0
 */
public interface IFeTreeService {
    void save(FeTree feTree);

    void deleteById(int id);

    void updateById(FeTree feTree);

    FeTree findOne(int id);

    List<FeTree> findAll();

    long count();
}
