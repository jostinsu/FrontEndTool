package me.sujianxin.persistence.repository;

import me.sujianxin.persistence.model.FeElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/5
 * <p>Time: 22:59
 * <p>Version: 1.0
 */
@Repository
public interface FeElementRepository extends JpaRepository<FeElement, Integer>, JpaSpecificationExecutor<FeElement> {
}
