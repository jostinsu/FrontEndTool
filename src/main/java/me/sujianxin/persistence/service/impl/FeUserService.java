package me.sujianxin.persistence.service.impl;

import me.sujianxin.persistence.model.FeUser;
import me.sujianxin.persistence.repository.FeUserRepository;
import me.sujianxin.persistence.service.IFeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/6
 * <p>Time: 23:35
 * <p>Version: 1.0
 */
@Service("feUserService")
public class FeUserService implements IFeUserService {
    @Autowired
    private FeUserRepository feUserRepository;

    @Override
    @Transactional
    public void save(FeUser feUser) {
        feUserRepository.save(feUser);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        feUserRepository.delete(id);
    }


    @Override
    @Transactional
    public int updateNickname(int id, String nickname) {
        return feUserRepository.updateNickname(id, nickname);
    }

    @Override
    @Transactional
    public int updatePassword(int id, String password) {
        return feUserRepository.updatePassword(id, password);
    }

    @Override
    public FeUser findOne(int id) {
        return feUserRepository.findOne(id);
    }

    @Override
    public Object[] login(String name, String password) {
        return feUserRepository.login(name, password);
    }

    @Override
    public Page<FeUser> findAll(Pageable pageable) {
        return feUserRepository.findAll(pageable);
    }

    @Override
    public long count() {
        return feUserRepository.count();
    }

    @Override
    public Page<FeUser> findBySpecification(String nickname, String password) {
        return feUserRepository.findAll(getSpecification(nickname, password), new PageRequest(0, 10, new Sort(Sort.Direction.DESC, new String[]{"id"})));
    }

    @Override
    public boolean existMail(String mail) {
        return feUserRepository.existMail(mail) != 0 ? true : false;
    }

    private Specification getSpecification(final String nickname, final String password) {
        return new Specification<FeUser>() {
            @Override
            public Predicate toPredicate(Root<FeUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // if(um.getName()!=null && um.getName().trim().length()>0){
                list.add(cb.like(root.get("nickname").as(String.class), nickname));
                //  }
                // if(um.getUuid()>0){
                list.add(cb.like(root.get("password").as(String.class), password));
                //   }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
    }
}
