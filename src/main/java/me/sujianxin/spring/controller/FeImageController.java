package me.sujianxin.spring.controller;

import me.sujianxin.spring.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
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

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("uploadFile") MultipartFile uploadFile,
                                      @RequestParam("projectName") String projectName, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (null != uploadFile && !uploadFile.isEmpty() && null != session.getAttribute("mail")) {
            int fileSizeLimit;
            try {
                fileSizeLimit = Integer.valueOf(environment.getProperty("file.upload.size"));
            } catch (Exception e) {
                fileSizeLimit = 10;
            }
            if (uploadFile.getSize() > fileSizeLimit * 1024 * 1024) {
                map.put("success", false);
                map.put("msg", "上传图片大小不允许超过" + fileSizeLimit + "MB");
                return map;
            }
            int index = uploadFile.getOriginalFilename().lastIndexOf(".");
            if (-1 != index) {
                String suffix = uploadFile.getOriginalFilename().substring(index + 1);
                String[] allowSuffix = environment.getProperty("file.upload.allowSuffix").split(";");
                boolean isOK = false;
                for (String tmp : allowSuffix) {
                    if (suffix.equalsIgnoreCase(tmp)) {
                        isOK = true;
                        break;
                    }
                }
                if (!isOK) {
                    map.put("success", false);
                    map.put("msg", "仅允许上传" + environment.getProperty("file.upload.allowSuffix") + "类型的文件");
                    return map;
                }
            }
            StringBuilder savePath = new StringBuilder(environment.getProperty("file.upload.path"));
            String mail = String.valueOf(session.getAttribute("mail"));
            savePath.append(File.separator).append(mail).append(File.separator).append(projectName)
                    .append(File.separator).append("image").append(File.separator);
            File rootFile = new File(savePath.toString());
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            File image = new File(savePath.append(uploadFile.getOriginalFilename()).toString());
            try {
                uploadFile.transferTo(image);
                map.put("success", true);
                map.put("msg", "上传图片成功");
                return map;
            } catch (IOException e) {
                map.put("success", false);
                map.put("msg", "上传图片失败，服务器错误");
                e.printStackTrace();
            }
        } else {
            map.put("success", false);
            map.put("msg", "未选中任何图片文件");
        }
        return map;
    }

    @RequestMapping(value = "rename", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> rename(@RequestParam("title") String title, @RequestParam("newName") String newName) {
        String originFilePath = environment.getProperty("file.upload.root") + title;
        boolean isSuccess = false;
        String newFilePath, msg;
        int index = title.lastIndexOf("/");
        StringBuilder sb = new StringBuilder(environment.getProperty("file.upload.root"));
        if (-1 != index) {
            newFilePath = sb.append(title.substring(0, index + 1)).append(newName).toString();
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
