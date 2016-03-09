package me.sujianxin.spring.controller;

import me.sujianxin.persistence.model.FeTree;
import me.sujianxin.persistence.service.IFeTreeService;
import me.sujianxin.spring.domain.FeTreeDomain;
import me.sujianxin.spring.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/9
 * <p>Time: 0:42
 * <p>Version: 1.0
 */
@Controller
public class FeTreeController {
    @Autowired
    private IFeTreeService feTreeService;

    @RequestMapping(value = "saveTree", method = RequestMethod.POST)
    public String save(@ModelAttribute FeTreeDomain feTreeDomain) {
        FeTree feTree = new FeTree();
        feTree.setName(feTreeDomain.getName());
        feTree.setIsFolder(feTreeDomain.getIsFolder());
        feTree.setLayer(feTreeDomain.getLayer());
        if (0 != feTreeDomain.getParentid()) {
            feTree.setTree(new FeTree(feTreeDomain.getParentid()));
        }
        feTreeService.save(feTree);
        return "";
    }

    @RequestMapping(value = "deleteTree", method = RequestMethod.DELETE)
    public Map<String, Object> delete(@RequestParam("id") int id) {
        feTreeService.deleteById(id);
        return MapUtil.deleteMap();
    }

    @RequestMapping(value = "updateTree", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@ModelAttribute FeTreeDomain feTreeDomain) {
        FeTree feTree = new FeTree();
        feTree.setId(feTreeDomain.getId());
        feTree.setName(feTreeDomain.getName());
        feTree.setIsFolder(feTreeDomain.getIsFolder());
        feTree.setLayer(feTreeDomain.getLayer());
        if (0 != feTreeDomain.getParentid()) {
            feTree.setTree(new FeTree(feTreeDomain.getParentid()));
        }
        feTreeService.updateById(feTree);
        return MapUtil.updateMap();
    }

    @RequestMapping(value = "tree/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findOne(@PathVariable("id") int id) {
        FeTree feTree = feTreeService.findOne(id);
        Map<String, Object> map = new HashMap<>(3);
        map.put("success", null != feTree ? true : false);
        map.put("msg", null != feTree ? "" : "非法操作");
        map.put("data", null != feTree ? feTree : "");
        return map;
    }

    @RequestMapping(value = "trees", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAll() {
        List<FeTree> feTree = feTreeService.findAll();
        Map<String, Object> map = new HashMap<>(3);
        map.put("success", !feTree.isEmpty() ? true : false);
        map.put("msg", !feTree.isEmpty() ? "" : "非法操作");
        map.put("data", !feTree.isEmpty() ? feTree : "");
        return map;
    }
}
