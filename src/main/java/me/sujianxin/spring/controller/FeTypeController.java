package me.sujianxin.spring.controller;

import me.sujianxin.persistence.model.FeType;
import me.sujianxin.persistence.service.IFeTypeService;
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
 * <p>Time: 0:43
 * <p>Version: 1.0
 */
@Controller
public class FeTypeController {
    @Autowired
    private IFeTypeService iFeTypeService;

    @RequestMapping(value = "saveType", method = RequestMethod.POST)
    public String save(@ModelAttribute FeType feType, BindingResult bindingResult) {
        iFeTypeService.save(feType);
        return "";
    }

    @RequestMapping(value = "deleteType", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("id") int id) {
        iFeTypeService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = "updateType", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@ModelAttribute FeType feType, BindingResult bindingResult) {
        iFeTypeService.updateById(feType);
        return MapUtil.getUpdateSuccessMap();
    }

//    @RequestMapping(value = "type/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> findOne(@PathVariable("id") int id) {
//        FeType feType = iFeTypeService.findOne(id);
//        Map<String, Object> map = new HashMap<>(3);
//        map.put("success", true);
//        map.put("msg", null != feType ? "" : "非法操作");
//        map.put("data", feType);
//        return map;
//    }

    @RequestMapping(value = "types", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAll() {
        List<FeType> feType = iFeTypeService.findAll();
        Map<String, Object> map = new HashMap<>(3);
        map.put("success", true);
        map.put("msg", !feType.isEmpty() ? "" : "组件不存在");
        map.put("data", feType);
        return map;
    }
}
