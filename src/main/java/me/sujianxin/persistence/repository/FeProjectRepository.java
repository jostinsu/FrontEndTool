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

    //@Query("select tree from FeTree tree join tree.projects as pr join pr.trees tr where tr.id=tree.id and pr.id=:projectid")
    //@Query("select project from FeProject project join project.trees as tree join tree.projects as pr where project.id=pr.id and project.id=:projectid")
    //FeProject findOneByProjectId(@Param("projectid") int projectid);
}
