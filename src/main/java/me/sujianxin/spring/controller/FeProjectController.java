package me.sujianxin.spring.controller;

import me.sujianxin.persistence.model.FeProject;
import me.sujianxin.persistence.model.FeUser;
import me.sujianxin.persistence.service.IFeProjectService;
import me.sujianxin.spring.domain.FeProjectDomain;
import me.sujianxin.spring.domain.FeProjectForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: sujianxin
 * <p>Date: 2016/3/4
 * <p>Time: 16:59
 * <p>Version: 1.0
 */
@Controller
public class FeProjectController {
    @Autowired
    private IFeProjectService feProjectService;

    @RequestMapping(value = {"/", "project"}, method = RequestMethod.GET)
    public String projectPage() {
        return "projectList";
    }

    @RequestMapping(value = "project", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> projectListByPage(@ModelAttribute FeProjectForm feProjectForm, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        Map<String, Object> result = feProjectService.findAll(feProjectForm);
        map.put("data", result.get("data"));
        map.put("iTotalRecords", result.get("count"));
        map.put("iTotalDisplayRecords", result.get("count"));
        return map;
    }

    @RequestMapping(value = {"newProject"}, method = RequestMethod.GET)
    public String projectNewPage() {
        return "newProject";
    }

    @RequestMapping(value = "newProject", method = RequestMethod.POST)
    public String projectPOST(@ModelAttribute FeProjectDomain feProjectDomain, HttpSession session, Model model) {
        FeProject feProject = new FeProject();
        feProject.setRemark(feProjectDomain.getRemark());
        feProject.setName(feProjectDomain.getName());
        feProject.setCreateTime(new Date());
        feProject.setUser(new FeUser(Integer.valueOf(String.valueOf(session.getAttribute("userId")))));
        feProjectService.save(feProject);
        model.addAttribute("success", true);
        model.addAttribute("msg", "保存成功");
        return "redirect:newProject";
    }

    @RequestMapping(value = "projectDelete", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> projectDELETE(int id, HttpSession session) {
        feProjectService.deleteById(id);
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("success", true);
        map.put("msg", "删除成功");
        return map;
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        request.getSession().setAttribute("userId", 1);
    }

}
