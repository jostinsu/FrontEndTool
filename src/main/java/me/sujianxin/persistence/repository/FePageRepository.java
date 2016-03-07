package me.sujianxin.persistence.repository;

import me.sujianxin.persistence.model.FePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/5
 * <p>Time: 22:58
 * <p>Version: 1.0
 */
@Repository
public interface FePageRepository extends JpaRepository<FePage, Integer> {
}
