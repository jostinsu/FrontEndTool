package me.sujianxin.spring.controller;

import me.sujianxin.persistence.model.FeProject;
import me.sujianxin.persistence.model.FeUser;
import me.sujianxin.persistence.service.IFeProjectService;
import me.sujianxin.spring.domain.FeProjectDomain;
import me.sujianxin.spring.domain.FeProjectForm;
import me.sujianxin.spring.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
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
    private IFeProjectService iFeProjectService;
    @Autowired
    private Environment environment;

    @RequestMapping(value = {"project"}, method = RequestMethod.GET)
    public String projectPage() {
        return "projectList";
    }

    @RequestMapping(value = "project", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> projectListByPage(@ModelAttribute FeProjectForm feProjectForm, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        int id = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
        Map<String, Object> result = iFeProjectService.findAll(feProjectForm, id);
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
        File uploadPath = new File(environment.getProperty("file.upload.path"));
        if (!uploadPath.exists()) {
            uploadPath.mkdir();
        }
        File userPath = new File(environment.getProperty("file.upload.path") + File.separator + String.valueOf(session.getAttribute("mail")));
        if (!userPath.exists()) {
            userPath.mkdir();
        }
        String projectPath = environment.getProperty("file.upload.path")
                + File.separator + String.valueOf(session.getAttribute("mail")) + File.separator + feProjectDomain.getName();
        File project = new File(projectPath);
        if (!project.exists()) {
            project.mkdir();
            FeProject feProject = new FeProject();
            feProject.setRemark(feProjectDomain.getRemark());
            feProject.setName(feProjectDomain.getName());
            feProject.setCreateTime(new Date());
            feProject.setUser(new FeUser(Integer.valueOf(String.valueOf(session.getAttribute("userid")))));
            iFeProjectService.save(feProject);
        } else {
            // TODO: 2016/3/15 默认创建css内容
        }
        model.addAttribute("success", !project.exists() ? true : false);
        model.addAttribute("msg", !project.exists() ? "保存成功" : "项目名称不能重复");
        return "redirect:newProject";
    }

    @RequestMapping(value = "projectDelete", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> projectDelete(@RequestParam("id") int id, HttpSession session) {
        iFeProjectService.deleteById(id);
        return MapUtil.deleteMap();
    }

    @RequestMapping(value = "updateProject", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@ModelAttribute FeProjectDomain feProjectDomain, HttpSession session) {
        FeProject feProject = new FeProject();
        feProject.setId(feProjectDomain.getId());
        feProject.setRemark(feProjectDomain.getRemark());
        feProject.setName(feProjectDomain.getName());
        feProject.setCreateTime(new Date());
        feProject.setUser(new FeUser(Integer.valueOf(String.valueOf(session.getAttribute("userid")))));
        iFeProjectService.updateById(feProject);
        return MapUtil.updateMap();
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        //request.getSession().setAttribute("userid", 1);
    }

}
