package me.sujianxin.persistence.repository;

import me.sujianxin.persistence.model.FeStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
public interface FeStyleRepository extends JpaRepository<FeStyle, Integer> {

    @Query("update FeStyle f set f.code=:code where f.id=:id")
    @Modifying
    int updateById(@Param("id") int id, @Param("code") String code);
}
