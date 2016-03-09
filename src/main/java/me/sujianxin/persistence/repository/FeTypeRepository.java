package me.sujianxin.persistence.repository;

import me.sujianxin.persistence.model.FeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/9
 * <p>Time: 0:53
 * <p>Version: 1.0
 */
@Repository
public interface FeTypeRepository extends JpaRepository<FeType, Integer> {
}
