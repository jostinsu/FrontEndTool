package me.sujianxin.spring.controller;

import me.sujianxin.persistence.model.FeProject;
import me.sujianxin.persistence.model.FeStyle;
import me.sujianxin.persistence.service.IFeStyleService;
import me.sujianxin.spring.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/9
 * <p>Time: 0:34
 * <p>Version: 1.0
 */
@Controller
public class FeStyleController {
    @Autowired
    private IFeStyleService feStyleService;

    @RequestMapping(value = "saveStyle", method = RequestMethod.POST)
    public String save(@ModelAttribute FeStyle feStyle, int projectid) {
        feStyle.setProject(new FeProject(projectid));
        feStyleService.save(feStyle);
        return "";
    }

    @RequestMapping(value = "deleteStyle", method = RequestMethod.DELETE)
    public Map<String, Object> delete(@RequestParam("id") int id) {
        feStyleService.deleteById(id);
        return MapUtil.deleteMap();
    }

    @RequestMapping(value = "updateStyle", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@ModelAttribute FeStyle feStyle) {
        feStyleService.updateById(feStyle.getId(), feStyle.getCode());
        return MapUtil.updateMap();
    }

    @RequestMapping(value = "style/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findOne(@PathVariable("id") int id) {
        Map<String, Object> map = new HashMap<>(3);
        FeStyle feStyle = feStyleService.findOne(id);
        map.put("success", null != feStyle ? true : false);
        map.put("msg", null != feStyle ? "" : "非法操作");
        map.put("data", null != feStyle ? feStyle : "");
        return map;
    }
}