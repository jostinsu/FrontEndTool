package me.sujianxin.spring.controller;

import me.sujianxin.persistence.model.FeUser;
import me.sujianxin.persistence.service.IFeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/7
 * <p>Time: 9:52
 * <p>Version: 1.0
 */
@Controller
public class FeUserController {
    @Autowired
    private IFeUserService feUserService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String init() {
        return "login";
    }


    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute FeUser feUser, HttpSession session) {
        Map<String, Object> map = new HashMap<>(2);
        feUserService.save(feUser);
        map.put("success", true);
        map.put("msg", "成功注册用户");
        return map;
    }

    @RequestMapping(value = "deleteUser", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteById(int id) {
        Map<String, Object> map = new HashMap<>(2);
        feUserService.deleteById(id);
        map.put("success", true);
        map.put("msg", "成功删除用户");
        return map;
    }

    @RequestMapping(value = "updateNickname", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateNickname(String nickname, HttpSession session) {
        Map<String, Object> map = new HashMap<>(2);
        int tmp = feUserService.updateNickname(Integer.valueOf(String.valueOf(session.getAttribute("userId"))), nickname);
        map.put("success", 1 == tmp ? true : false);
        map.put("msg", 1 == tmp ? "成功修改昵称" : "修改昵称失败");
        return map;
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePassword(String password, HttpSession session, Model model) {
        Map<String, Object> map = new HashMap<>(2);
        int tmp = feUserService.updatePassword(Integer.valueOf(String.valueOf(session.getAttribute("userId"))), password);
        map.put("success", true);
        model.addAttribute("success", true);
        model.addAttribute("msg", "成功修改密码");
        map.put("msg", "成功修改密码");
        return "resetPassword";
    }

    @RequestMapping(value = "userInfo", method = RequestMethod.GET)
    public String findOne(HttpSession session, Model model) {
        model.addAttribute("feUser", feUserService.findOne(Integer.valueOf(String.valueOf(session.getAttribute("userId")))));
        return "account";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(String name, String password) {
        Map<String, Object> map = new HashMap<>(3);
        Object[] tmp = feUserService.login(name, password);
        if (tmp.length > 0) {
            map.put("success", true);
            map.put("msg", "登录成功");
            map.put("data", tmp);

        } else {
            map.put("success", false);
            map.put("msg", "请检测账号/密码是否正确");
        }
        return map;
    }

    @RequestMapping(value = "userAll", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAll(int page, int pageSize) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", true);
        map.put("msg", "登录成功");
        Pageable pageable = new PageRequest(page,
                pageSize, new Sort(Sort.Direction.ASC, "nickname"));
        map.put("data", feUserService.findAll(pageable));
        map.put("iTotalRecords", feUserService.count());
        map.put("iTotalDisplayRecords", feUserService.count());
        return map;
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        request.getSession().setAttribute("userId", 1);
    }

}
