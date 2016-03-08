package me.sujianxin.persistence.repository;

import me.sujianxin.persistence.model.FeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
public interface FeUserRepository extends JpaRepository<FeUser, Integer>, JpaSpecificationExecutor<FeUser> {
    @Query("select f.id,f.nickname,f.mail,f.password from FeUser f where f.mail=:account and f.password=:password")
    Object[] login(@Param("account") String account, @Param("password") String password);

    @Query("update FeUser f set f.nickname=:nickname where f.id=:id")
    @Modifying
    int updateNickname(@Param("id") int id, @Param("nickname") String nickname);

    @Query("update FeUser f set f.password=:password where f.id=:id")
    @Modifying
    int updatePassword(@Param("id") int id, @Param("password") String password);

    @Query("select count(*) from FeUser f where f.mail=:mail")
    int existMail(@Param("mail") String mail);
}
