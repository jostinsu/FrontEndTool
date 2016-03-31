package me.sujianxin.spring.controller;

import com.google.common.base.Strings;
import me.sujianxin.persistence.model.FePage;
import me.sujianxin.persistence.model.FeTree;
import me.sujianxin.persistence.service.IFeTreeService;
import me.sujianxin.spring.domain.FeTreeDomain;
import me.sujianxin.spring.util.MapUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
public class FeTreeController {
    @Autowired
    private IFeTreeService iFeTreeService;
    @Autowired
    private Environment environment;

    @RequestMapping(value = "saveTree", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute FeTreeDomain feTreeDomain, BindingResult bindingResult) {
        FeTree feTree = new FeTree();
        feTree.setName(feTreeDomain.getName());
        feTree.setIsFolder(feTreeDomain.getIsFolder());
        feTree.setIconSkin(feTreeDomain.getIconSkin());
        if (null != feTreeDomain.getIsFolder() && "0".equals(feTreeDomain.getIsFolder())) {
            FePage fePage = new FePage();
            fePage.setDownloadCode("");
            fePage.setMultipleCode("");
            feTree.addPage(fePage);
        }
        if (0 != feTreeDomain.getParentid()) {
            feTree.setTree(new FeTree(feTreeDomain.getParentid()));
        }
        FeTree reFeTree = iFeTreeService.save(feTree);
        Map<String, Object> map = new HashMap<>(3);
        map.put("id", reFeTree.getId());
        map.put("success", true);
        map.put("msg", "新建成功");
        return map;
    }

    @RequestMapping(value = "deleteTree", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("id") int id) {
        iFeTreeService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = "updateTree", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@ModelAttribute FeTreeDomain feTreeDomain, BindingResult bindingResult) {
        FeTree feTree = new FeTree();
        feTree.setId(feTreeDomain.getId());
        feTree.setName(feTreeDomain.getName());
        feTree.setIsFolder(feTreeDomain.getIsFolder());
        feTree.setIconSkin(feTreeDomain.getIconSkin());
        if (0 != feTreeDomain.getParentid()) {
            feTree.setTree(new FeTree(feTreeDomain.getParentid()));
        }
        iFeTreeService.updateById(feTree);
        return MapUtil.getUpdateSuccessMap();
    }

    @RequestMapping(value = "renameTreeNodeName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateTreeName(@ModelAttribute FeTreeDomain feTreeDomain, BindingResult bindingResult) {
        int tmp = iFeTreeService.updateNameById(feTreeDomain.getId(), feTreeDomain.getName());
        return 1 == tmp ? MapUtil.getUpdateSuccessMap() : MapUtil.getUpdateFailMap();
    }

    @RequestMapping(value = "updatePageCodeByTreeId", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePageCodeByTreeId(@RequestParam("id") String id, @RequestParam("code") String code) {
        int tmp = 0;
        if (!Strings.isNullOrEmpty(id) && NumberUtils.isNumber(id) && !Strings.isNullOrEmpty(code)) {
            tmp = iFeTreeService.updatePageByTreeId(Integer.valueOf(id), code);
        }
        return 1 == tmp ? MapUtil.getUpdateSuccessMap() : MapUtil.getUpdateFailMap();
    }

//    @RequestMapping(value = "tree/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> findOne(@PathVariable("id") int id) {
//        FeTree feTree = iFeTreeService.findOne(id);
//        Map<String, Object> map = new HashMap<>(3);
//        map.put("success", true);
//        map.put("msg", null != feTree ? "" : "非法操作");
//        map.put("data", null != feTree ? feTree : "");
//        return map;
//    }

}
