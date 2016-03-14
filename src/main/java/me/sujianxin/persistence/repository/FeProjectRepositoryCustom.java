package me.sujianxin.persistence.repository;

import me.sujianxin.spring.domain.FeProjectForm;

import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: sujianxin
 * <p>Date: 2016/3/4
 * <p>Time: 19:15
 * <p>Version: 1.0
 */
public interface FeProjectRepositoryCustom {

    Map<String, Object> findByPage(FeProjectForm feProjectForm, int userid);
}