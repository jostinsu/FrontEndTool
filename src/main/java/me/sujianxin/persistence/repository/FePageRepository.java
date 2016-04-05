package me.sujianxin.persistence.repository;

import me.sujianxin.persistence.model.FePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Modifying
    @Query("update FePage page set page.downloadCode=:downloadCode,page.multipleCode=:multipleCode where page.id=:id")
    int updateCode(@Param("id") int id, @Param("multipleCode") String multipleCode, @Param("downloadCode") String downloadCode);
}
