package me.sujianxin.spring.controller;

import me.sujianxin.spring.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/18
 * <p>Time: 20:20
 * <p>Version: 1.0
 */
@Controller
public class FeImageController {
    @Autowired
    private Environment environment;

    @RequestMapping(value = "rename", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> rename(@RequestParam("title") String title, @RequestParam("newName") String newName) {
        String originFilePath = environment.getProperty("file.upload.root") + title;
        boolean isSuccess = false;
        String newFilePath, msg;
        int index = title.lastIndexOf("/");
        StringBuffer sb = new StringBuffer(environment.getProperty("file.upload.root"));
        if (-1 != index) {
            newFilePath = sb.append(title.substring(0, index + 1)).append(newName).toString();
            System.out.println(originFilePath);
            System.out.println(newFilePath);
            File origin = new File(originFilePath);
            File renamefile = new File(newFilePath);
            if (origin.exists() && !renamefile.exists()) {
                isSuccess = origin.renameTo(renamefile);
                msg = isSuccess ? "成功修改名称" : "服务器错误，请稍后再试";
            } else {
                msg = "原文件不存在或新文件名重复";
            }
        } else {
            msg = "参数非法";
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("success", isSuccess);
        map.put("msg", msg);
        return map;
    }

    @RequestMapping(value = "deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("title") String title) {
        String originFilePath = environment.getProperty("file.upload.root") + title;
        File origin = new File(originFilePath);
        if (origin.exists() && origin.isFile()) {
            origin.delete();
            return MapUtil.getImageDeleteSuccessMap();
        }
        return MapUtil.getImageDeleteFailMap();
    }
}
