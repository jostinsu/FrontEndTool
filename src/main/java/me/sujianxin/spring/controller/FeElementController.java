package me.sujianxin.spring.controller;

import me.sujianxin.persistence.model.FeElement;
import me.sujianxin.persistence.model.FeType;
import me.sujianxin.persistence.service.IFeElementService;
import me.sujianxin.spring.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
public class FeElementController {
    @Autowired
    private IFeElementService iFeElementService;

    @RequestMapping(value = "saveElement", method = RequestMethod.POST)
    public String save(@ModelAttribute FeElement feElement, BindingResult bindingResult, @RequestParam("typeid") int typeid) {
        feElement.setType(new FeType(typeid));
        iFeElementService.save(feElement);
        return "";
    }

    @RequestMapping(value = "deleteElement", method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestParam("id") int id) {
        iFeElementService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = "updateElement", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@ModelAttribute FeElement feElement, BindingResult bindingResult, int typeid) {
        feElement.setType(new FeType(typeid));
        iFeElementService.updateById(feElement);
        return MapUtil.getUpdateSuccessMap();
    }

    @RequestMapping(value = "element/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findByTypeId(@PathVariable("id") int id) {
        List<FeElement> feElementList = iFeElementService.findByTypeId(id);
        Map<String, Object> map = new HashMap<>(3);
        map.put("success", true);
        map.put("msg", "");
        map.put("data", feElementList);
        return map;
    }
}
