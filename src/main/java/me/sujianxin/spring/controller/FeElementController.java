package me.sujianxin.spring.controller;

import me.sujianxin.persistence.model.FeElement;
import me.sujianxin.persistence.model.FeType;
import me.sujianxin.persistence.service.IFeElementService;
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
 * <p>Time: 0:42
 * <p>Version: 1.0
 */
@Controller
public class FeElementController {
    @Autowired
    private IFeElementService iFeElementService;

    @RequestMapping(value = "saveElement", method = RequestMethod.POST)
    public String save(@ModelAttribute FeElement feElement, @RequestParam("typeid") int typeid) {
        feElement.setType(new FeType(typeid));
        iFeElementService.save(feElement);
        return "";
    }

    @RequestMapping(value = "deleteElement", method = RequestMethod.DELETE)
    public Map<String, Object> delete(@RequestParam("id") int id) {
        iFeElementService.deleteById(id);
        return MapUtil.deleteMap();
    }

    @RequestMapping(value = "updateElement", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@ModelAttribute FeElement feElement, int typeid) {
        feElement.setType(new FeType(typeid));
        iFeElementService.updateById(feElement);
        return MapUtil.updateMap();
    }

    @RequestMapping(value = "element/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findOne(@PathVariable("id") int id) {
        FeElement feElement = iFeElementService.findOne(id);
        Map<String, Object> map = new HashMap<>(3);
        map.put("success", null != feElement ? true : false);
        map.put("msg", null != feElement ? "" : "非法操作");
        map.put("data", null != feElement ? feElement : "");
        return map;
    }
}
