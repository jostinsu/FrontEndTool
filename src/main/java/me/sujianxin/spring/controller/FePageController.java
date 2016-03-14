package me.sujianxin.spring.controller;

import me.sujianxin.persistence.model.FePage;
import me.sujianxin.persistence.model.FeTree;
import me.sujianxin.persistence.service.IFePageService;
import me.sujianxin.spring.domain.FePageDomain;
import me.sujianxin.spring.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/8
 * <p>Time: 22:57
 * <p>Version: 1.0
 */
@Controller
public class FePageController {

    @Autowired
    private IFePageService iFePageService;

    @RequestMapping(value = "savePage", method = RequestMethod.POST)
    public String save(@ModelAttribute FePageDomain fePageDomain, Model model) {
        FePage fePage = new FePage();
        fePage.setTree(new FeTree(fePageDomain.getTreeid()));
        fePage.setStyle(fePageDomain.getStyle());
        fePage.setCode(fePageDomain.getCode());
        iFePageService.save(fePage);
        Map<String, Object> map = new HashMap<>(2);
        map.put("success", true);
        map.put("msg", "保存成功");
        return "";
    }

    @RequestMapping(value = "deletePage", method = RequestMethod.DELETE)
    public Map<String, Object> deletePageById(@RequestParam("id") int id) {
        iFePageService.deleteById(id);
        return MapUtil.deleteMap();
    }

    @RequestMapping(value = "updatePage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePage(@ModelAttribute FePageDomain fePageDomain) {
        FePage fePage = new FePage();
        fePage.setId(fePageDomain.getId());
        fePage.setCode(fePageDomain.getCode());
        fePage.setStyle(fePageDomain.getStyle());
        fePage.setTree(new FeTree(fePageDomain.getTreeid()));
        iFePageService.updateById(fePage);
        return MapUtil.updateMap();
    }

    @RequestMapping(value = "page/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> pageInfo(@PathVariable("id") int id) {
        FePage fePage = iFePageService.findOne(id);
        Map<String, Object> map = new HashMap<>(3);
        map.put("success", null != fePage ? true : false);
        map.put("msg", null != fePage ? "" : "非法操作");
        map.put("data", null != fePage ? fePage : "");
        return map;
    }
}
