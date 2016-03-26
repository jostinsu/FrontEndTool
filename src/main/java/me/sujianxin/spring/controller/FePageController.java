package me.sujianxin.spring.controller;

import com.google.common.base.Strings;
import me.sujianxin.persistence.service.IFePageService;
import me.sujianxin.spring.util.MapUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

//    @RequestMapping(value = "savePage", method = RequestMethod.POST)
//    public String save(@ModelAttribute FePageDomain fePageDomain, Model model) {
//        FePage fePage = new FePage();
//        fePage.setTree(new FeTree(fePageDomain.getTreeid()));
//        fePage.setStyle(fePageDomain.getStyle());
//        fePage.setCode(fePageDomain.getCode());
//        iFePageService.save(fePage);
//        Map<String, Object> map = new HashMap<>(2);
//        map.put("success", true);
//        map.put("msg", "保存成功");
//        return "";
//    }

//    @RequestMapping(value = "deletePage", method = RequestMethod.POST)
//    public Map<String, Object> deletePageById(@RequestParam("id") int id) {
//        iFePageService.deleteById(id);
//        return MapUtil.getDeleteMap();
//    }

//    @RequestMapping(value = "updatePage", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> updatePage(@ModelAttribute FePageDomain fePageDomain) {
//        FePage fePage = new FePage();
//        fePage.setId(fePageDomain.getId());
//        fePage.setCode(fePageDomain.getCode());
//        fePage.setStyle(fePageDomain.getStyle());
//        fePage.setTree(new FeTree(fePageDomain.getTreeid()));
//        iFePageService.updateById(fePage);
//        return MapUtil.getUpdateSuccessMap();
//    }

    @RequestMapping(value = "updatePageCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePageCode(@RequestParam("id") String id, @RequestParam("code") String code) {
        int tmp = 0;
        if (!Strings.isNullOrEmpty(id) && NumberUtils.isNumber(id) && !Strings.isNullOrEmpty(code)) {
            tmp = iFePageService.updateCode(Integer.valueOf(id), code);
        }
        return 1 == tmp ? MapUtil.getUpdateSuccessMap() : MapUtil.getUpdateFailMap();
    }

//    @RequestMapping(value = "page/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> pageInfo(@PathVariable("id") int id) {
//        FePage fePage = iFePageService.findOne(id);
//        Map<String, Object> map = new HashMap<>(3);
//        map.put("success", true);
//        map.put("msg", null != fePage ? "" : "非法操作");
//        map.put("data", null != fePage ? fePage : "");
//        return map;
//    }
}
