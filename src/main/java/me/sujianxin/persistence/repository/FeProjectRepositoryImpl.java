package me.sujianxin.persistence.repository;

import com.google.common.base.Strings;
import me.sujianxin.spring.domain.FeProjectDomain;
import me.sujianxin.spring.domain.FeProjectForm;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: sujianxin
 * <p>Date: 2016/3/4
 * <p>Time: 19:15
 * <p>Version: 1.0
 */
@Repository
public class FeProjectRepositoryImpl implements FeProjectRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, Object> findByPage(FeProjectForm feProjectForm, int userid) {
        Map<String, Object> map = new HashMap<>(2);
        StringBuilder sb = new StringBuilder("select new me.sujianxin.spring.domain.FeProjectDomain(f.id,f.name,f.createTime,f.remark) from FeProject f where f.user.id=:userid");
        StringBuilder sbCount = new StringBuilder("select count(*) from FeProject f where f.user.id=:userid");

        if (!Strings.isNullOrEmpty(feProjectForm.getName())) {
            sb.append(" and f.name like CONCAT('%',:name,'%')");
            sbCount.append(" and f.name like CONCAT('%',:name,'%')");
            if (!Strings.isNullOrEmpty(feProjectForm.getFromTime()) && !Strings.isNullOrEmpty(feProjectForm.getToTime())) {
                sb.append(" and f.createTime between :fromTime and :toTime");
                sbCount.append(" and f.createTime between :fromTime and :toTime");
            }
        } else {
            if (!Strings.isNullOrEmpty(feProjectForm.getFromTime()) && !Strings.isNullOrEmpty(feProjectForm.getToTime())) {
                sb.append(" and f.createTime between :fromTime and :toTime");
                sbCount.append(" and f.createTime between :fromTime and :toTime");
            }
        }

        sb.append(" order by f.").append(feProjectForm.getSortCol()).append(" ").append(feProjectForm.getSortDir());
        System.out.println("sb.toString()=" + sb.toString());
        TypedQuery<FeProjectDomain> query = entityManager.createQuery(sb.toString(), FeProjectDomain.class);
        TypedQuery<Long> queryCount = entityManager.createQuery(sbCount.toString(), Long.class);

        if (!Strings.isNullOrEmpty(feProjectForm.getName())) {
            query.setParameter("name", feProjectForm.getName());
            queryCount.setParameter("name", feProjectForm.getName());
        }
        if (!Strings.isNullOrEmpty(feProjectForm.getFromTime()) && !Strings.isNullOrEmpty(feProjectForm.getToTime())) {
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                query.setParameter("fromTime", format.parse(feProjectForm.getFromTime()));
                query.setParameter("toTime", format.parse(feProjectForm.getToTime()));
                queryCount.setParameter("fromTime", format.parse(feProjectForm.getFromTime()));
                queryCount.setParameter("toTime", format.parse(feProjectForm.getToTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                Calendar tmpFromTime = new GregorianCalendar(1970, 1, 1, 0, 0, 0);
                Calendar tmpToTime = new GregorianCalendar(2099, 1, 1, 0, 0, 0);
                query.setParameter("fromTime", tmpFromTime.getTime());
                query.setParameter("toTime", tmpToTime.getTime());
                queryCount.setParameter("fromTime", tmpFromTime.getTime());
                queryCount.setParameter("toTime", tmpToTime.getTime());
            }
        }
        query.setParameter("userid", userid);
        queryCount.setParameter("userid", userid);
        query.setFirstResult((feProjectForm.getPage() - 1) * feProjectForm.getPageSize());//前端页面计数从1开始
        query.setMaxResults(feProjectForm.getPageSize());
        map.put("data", query.getResultList());
        map.put("count", queryCount.getSingleResult());
        return map;
    }
}
