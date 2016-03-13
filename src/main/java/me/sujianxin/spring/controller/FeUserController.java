package me.sujianxin.spring.controller;

import me.sujianxin.persistence.model.FeUser;
import me.sujianxin.persistence.service.IFeUserService;
import me.sujianxin.spring.util.MapUtil;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

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

    @RequestMapping(value = "updatePassword", method = RequestMethod.GET)
    public String updatePassword() {
        return "updatePassword";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePassword(String password, String newPassword1, String newPassword2, HttpSession session, Model model) {
        boolean check;
        int tmp = 0;
        boolean isSame = !isNullOrEmpty(newPassword1) && !isNullOrEmpty(newPassword2) && newPassword1.equals(newPassword2);
        if (check = !isNullOrEmpty(password)) {
            int id = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
            FeUser feUser = feUserService.findOne(id);
            if (null != feUser && feUser.getPassword().equals(password) && isSame) {
                tmp = feUserService.updatePassword(id, newPassword1);
            }
        }
        model.addAttribute("success", check && 1 == tmp ? true : false);
        model.addAttribute("msg", check && 1 == tmp ? "成功修改密码" : "修改密码失败");
        return "redirect:updatePassword";
    }

    @RequestMapping(value = "userInfo", method = RequestMethod.GET)
    public String findOne(HttpSession session, Model model) {
        model.addAttribute("feUser", feUserService.findOne(Integer.valueOf(String.valueOf(session.getAttribute("userid")))));
        return "account";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute FeUser feUser, HttpSession session) {
        Map<String, Object> map = new HashMap<>(2);
        boolean tmp = feUserService.existMail(feUser.getMail());
        if (!tmp) {
            feUser.setRegisterTime(new Date());
            feUserService.save(feUser);
        }
        map.put("success", !tmp ? true : false);
        map.put("msg", !tmp ? "成功注册用户" : "该邮箱已经被注册");
        return map;
    }

    //@RequestMapping(value = "deleteUser", method = RequestMethod.DELETE)
    //@ResponseBody
    public Map<String, Object> deleteById(int id) {
        feUserService.deleteById(id);
        return MapUtil.deleteMap();
    }

    @RequestMapping(value = "updateNickname", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateNickname(String nickname, HttpSession session) {
        Map<String, Object> map = new HashMap<>(2);
        int tmp = 0;
        boolean b;
        if (b = !isNullOrEmpty(nickname)) {
            tmp = feUserService.updateNickname(1, nickname);
        }
        map.put("success", 1 == tmp && b ? true : false);
        map.put("msg", 1 == tmp && b ? "成功修改昵称" : "修改昵称失败");
        return map;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String name, String password, HttpSession session, Model model) {
        boolean validate = !isNullOrEmpty(name) && !isNullOrEmpty(password);
        Object[] tmp = {};
        boolean b = false;
        if (validate) {
            tmp = feUserService.login(name, password);
            if (b = tmp.length > 0) {
                session.setAttribute("userid", tmp[0]);//f.id,f.nickname,f.mail,f.password
                session.setAttribute("nickname", tmp[1]);
                model.addAttribute("userid", tmp[0]);
                model.addAttribute("nickname", tmp[1]);
                return "redirect:";
            }
        }
        model.addAttribute("success", validate && b ? true : false);
        model.addAttribute("msg", validate && b ? "" : "请检测账号/密码是否正确");
        return "redirect:";
    }

    //@RequestMapping(value = "existMail", method = RequestMethod.POST)
    //@ResponseBody
    public Map<String, Object> existMail(String mail) {
        Map<String, Object> map = new HashMap<>(5);
        boolean tmp = feUserService.existMail(mail);
        map.put("success", tmp ? false : true);
        map.put("msg", tmp ? "该邮箱已经被注册" : "邮箱可用");
        return map;
    }

    //@RequestMapping(value = "userAll", method = RequestMethod.GET)
    //@ResponseBody
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
        System.out.println("in FeUserController initBinder");
        request.getSession().setAttribute("userid", 1);
    }

}
