package me.sujianxin.spring.controller;

import me.sujianxin.persistence.model.FeUser;
import me.sujianxin.persistence.service.IFeUserService;
import me.sujianxin.spring.events.EmailEvent;
import me.sujianxin.spring.util.MapUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
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
public class FeUserController implements ApplicationContextAware {
    @Autowired
    private IFeUserService iFeUserService;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @RequestMapping(value = {"/", "login"}, method = RequestMethod.GET)
    public String init() {
        return "user";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String mail, String password, HttpSession session, Model model) {
        boolean validate = !isNullOrEmpty(mail) && !isNullOrEmpty(password);
        FeUser tmp = null;
        boolean b = false;
        if (validate) {
            tmp = iFeUserService.login(mail, password);
            if (b = (null != tmp)) {
                session.setAttribute("userid", tmp.getId());//f.id,f.nickname,f.mail,f.password
                session.setAttribute("name", null != tmp.getNickname() ? tmp.getNickname() : tmp.getMail());
                session.setAttribute("mail", mail);
                return "redirect:project";
            }
        }
        model.addAttribute("success", validate && b);
        model.addAttribute("loginMsg", "请检查账号/密码是否正确");
        model.addAttribute("page", "login");
        return "user";
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
            FeUser feUser = iFeUserService.findOne(id);
            if (null != feUser && feUser.getPassword().equals(password) && isSame) {
                tmp = iFeUserService.updatePassword(id, newPassword1);
            }
        }
        model.addAttribute("success", check && 1 == tmp);
        model.addAttribute("msg", check && 1 == tmp ? "成功修改密码" : "原密码输入不正确，修改密码失败");
        return "redirect:updatePassword";
    }

    @RequestMapping(value = "userInfo", method = RequestMethod.GET)
    public String findOne(HttpSession session, Model model) {
        model.addAttribute("feUser", iFeUserService.findOne(Integer.valueOf(String.valueOf(session.getAttribute("userid")))));
        return "mail";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String save(String mail, String password1, HttpServletRequest request, Model model, HttpSession session) {
        boolean tmp = iFeUserService.existMail(mail);
        if (!tmp) {
            FeUser feUser = new FeUser();
            feUser.setMail(mail);
            feUser.setPassword(password1);
            //feUser.setRegisterTime(new Date());
            feUser.setStatus("normal");
            FeUser feUser1 = iFeUserService.save(feUser);
            session.setAttribute("userid", feUser1.getId());
            session.setAttribute("name", feUser1.getMail());
            session.setAttribute("mail", feUser1.getMail());
            //发送注册邮件
            StringBuffer sb = new StringBuffer(
                    "<html><head><meta http-equiv='content-type' content='text/html; charset=GBK'></head><body>尊敬的")
                    .append(mail)
                    .append(",您好</br>感谢使用前端可视化工具</br>")
                    .append("<a href=\"")
                    .append(request.getScheme())
                    .append("://")
                    .append(request.getServerName())
                    .append(":")
                    .append(request.getServerPort())
                    .append(request.getContextPath())
                    .append("/?time=")
                    .append(new Date().getTime())
                    .append("\">")
                    .append("点击这里登录")
                    .append("</a></br></body></html>");
            EmailEvent emailEvent = new EmailEvent(this, mail, "注册成功-前端可视化工具服务邮件", sb.toString());
            applicationContext.publishEvent(emailEvent);
            return "redirect:project";
        }
        model.addAttribute("success", !tmp);
        model.addAttribute("registerMsg", !tmp ? "成功注册用户" : "该邮箱已经被注册");
        model.addAttribute("page", "register");
        return "user";
    }

    //@RequestMapping(value = "deleteUser", method = RequestMethod.POST)
    //@ResponseBody
    public Map<String, Object> deleteById(int id) {
        iFeUserService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = "updateNickname", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateNickname(String nickname, HttpSession session) {
        Map<String, Object> map = new HashMap<>(2);
        int tmp = 0;
        boolean b;
        if (b = !isNullOrEmpty(nickname)) {
            int id = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
            tmp = iFeUserService.updateNickname(id, nickname);
        }
        session.setAttribute("name", nickname);
        map.put("success", 1 == tmp && b);
        map.put("msg", 1 == tmp && b ? "成功修改昵称" : "修改昵称失败");
        return map;
    }


    //@RequestMapping(value = "existMail", method = RequestMethod.POST)
    //@ResponseBody
    public Map<String, Object> existMail(String mail) {
        Map<String, Object> map = new HashMap<>(5);
        boolean tmp = iFeUserService.existMail(mail);
        map.put("success", tmp);
        map.put("msg", tmp ? "该邮箱已经被注册" : "邮箱可用");
        return map;
    }

    //@RequestMapping(value = "userAll", method = RequestMethod.GET)
    //@ResponseBody
    public Map<String, Object> findAll(int page, int pageSize) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", true);
        map.put("msg", "");
        Pageable pageable = new PageRequest(page,
                pageSize, new Sort(Sort.Direction.ASC, "nickname"));
        Page<FeUser> feUserPage = iFeUserService.findAll(pageable);
        map.put("data", feUserPage.getContent());
        map.put("iTotalRecords", feUserPage.getTotalElements());
        map.put("iTotalDisplayRecords", feUserPage.getNumberOfElements());
        return map;
    }

    @RequestMapping(value = "resetRequest", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> resetRequest(@RequestParam("mail") String mail, HttpServletRequest request) {
        FeUser feUser = iFeUserService.findByMailEquals(mail);
        if (null != feUser) {
            feUser.setStatus("resetting");
            iFeUserService.save(feUser);
            StringBuffer sb = new StringBuffer(
                    "<html><head><meta http-equiv='content-type' content='text/html; charset=GBK'></head><body>尊敬的")
                    .append(!isNullOrEmpty(feUser.getNickname()) ? feUser.getNickname() : feUser.getMail())
                    .append(",您好</br><b>温馨提示</b>：重置密码链接只能使用一次，24小时内有效</br>")
                    .append("<a href=\"")
                    .append(request.getScheme())
                    .append("://")
                    .append(request.getServerName())
                    .append(":")
                    .append(request.getServerPort())
                    .append(request.getContextPath())
                    .append("/")
                    .append("resetPassword?mail=")
                    .append(mail)
                    .append("&time=")
                    .append(new Date().getTime())
                    .append("\">")
                    .append("点击这里进入重置密码页面,如非本人操作请忽略")
                    .append("</a></br></body></html>");
            EmailEvent emailEvent = new EmailEvent(this, mail, "重置密码申请-前端可视化工具服务邮件", sb.toString());
            applicationContext.publishEvent(emailEvent);
            return MapUtil.getUserPasswordResetRequstSuccessMap();
        }
        return MapUtil.getUserPasswordResetRequstFailMap();
    }

    @RequestMapping(value = "resetPassword", method = RequestMethod.GET)
    public String resetPasswordPage(@RequestParam("mail") String mail, @RequestParam("time") String time, HttpSession session, Model model) {
        if (NumberUtils.isNumber(time)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Integer.valueOf(time) + 24 * 60 * 60 * 1000);
            if (calendar.getTime().before(new Date()) && !isNullOrEmpty(mail)) {
                session.setAttribute("accountTmp", mail);
                return "resetPassword";//返回重置密码页面
            }
        }
        model.addAttribute("success", false);
        model.addAttribute("msg", "链接失效");
        return "redirect:login";//返回登录页面
    }

    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public String resetPassword(@RequestParam("password") String password, Model model, HttpSession session) {
        String mail = null != session.getAttribute("accountTmp") ? String.valueOf(session.getAttribute("accountTmp")) : "";
        if (!isNullOrEmpty(mail)) {
            FeUser feUser = iFeUserService.findByMailEquals(mail);
            if ("resetting".equals(feUser.getStatus())) {
                feUser.setPassword(password);
                feUser.setStatus("normal");
                iFeUserService.save(feUser);
                model.addAttribute("success", true);
                model.addAttribute("msg", "成功重置密码");
                session.removeAttribute("accountTmp");
                return "redirect:login";
            }
        }
        model.addAttribute("success", false);
        model.addAttribute("msg", "非法操作");
        return "redirect:login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
    }

}
