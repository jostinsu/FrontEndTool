package me.sujianxin.persistence.repository;

import me.sujianxin.persistence.model.FeTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/5
 * <p>Time: 22:59
 * <p>Version: 1.0
 */
@Repository
public interface FeTreeRepository extends JpaRepository<FeTree, Integer> {
    @Query("select tree from FeTree tree join tree.projects as pr join pr.trees tr where tr.id=tree.id and pr.id=:projectid")
    FeTree findOneByProjectId(@Param("projectid") int projectid);
}
