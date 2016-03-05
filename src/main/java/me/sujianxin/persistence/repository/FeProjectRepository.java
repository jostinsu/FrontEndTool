package me.sujianxin.persistence.repository;

import me.sujianxin.persistence.model.FeProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Created with IDEA
 * <p>Author: sujianxin
 * <p>Date: 2016/3/3
 * <p>Time: 22:28
 * <p>Version: 1.0
 */
@Repository
public interface FeProjectRepository extends JpaRepository<FeProject, Integer>, FeProjectRepositoryCustom {
}
