package me.sujianxin.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import me.sujianxin.persistence.model.FeProject;
import me.sujianxin.persistence.model.FeStyle;
import me.sujianxin.persistence.model.FeTree;
import me.sujianxin.persistence.model.FeUser;
import me.sujianxin.persistence.service.IFeProjectService;
import me.sujianxin.spring.domain.FeProjectDomain;
import me.sujianxin.spring.domain.FeProjectForm;
import me.sujianxin.spring.util.MapUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.apache.commons.lang3.math.NumberUtils.isNumber;

/**
 * <p>Created with IDEA
 * <p>Author: sujianxin
 * <p>Date: 2016/3/4
 * <p>Time: 16:59
 * <p>Version: 1.0
 */
@Controller
public class FeProjectController {
    @Autowired
    private IFeProjectService iFeProjectService;
    @Autowired
    private Environment environment;

    @RequestMapping(value = {"project"}, method = RequestMethod.GET)
    public String projectPage() {
        return "projectList";
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.GET)
    public String editPage(@RequestParam("id") String id, Model model) {
        model.addAttribute("id", id);
        return "edit";
    }

    @RequestMapping(value = "project", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> projectListByPage(@ModelAttribute FeProjectForm feProjectForm, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        int id = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
        Map<String, Object> result = iFeProjectService.findAll(feProjectForm, id);
        map.put("data", result.get("data"));
        map.put("iTotalRecords", result.get("count"));
        map.put("iTotalDisplayRecords", result.get("count"));
        return map;
    }

    @RequestMapping(value = {"newProject"}, method = RequestMethod.GET)
    public String projectNewPage() {
        return "newProject";
    }

    @RequestMapping(value = "newProject", method = RequestMethod.POST)
    public String projectPOST(@ModelAttribute FeProjectDomain feProjectDomain, HttpSession session, Model model) {
//        File uploadPath = new File(environment.getProperty("file.upload.path"));
//        if (!uploadPath.exists()) {
//            uploadPath.mkdirs();
//        }
//        File userPath = new File(environment.getProperty("file.upload.path") + File.separator + String.valueOf(session.getAttribute("mail")));
//        if (!userPath.exists()) {
//            userPath.mkdirs();
//        }
        String projectPath = environment.getProperty("file.upload.path")
                + File.separator + String.valueOf(session.getAttribute("mail")) + File.separator + feProjectDomain.getName();
        File project = new File(projectPath);
        if (!project.exists()) {
            project.mkdirs();
            new File(projectPath + File.separator + "image").mkdir();
            FeProject feProject = new FeProject();
            feProject.setRemark(feProjectDomain.getRemark());
            feProject.setName(feProjectDomain.getName());
            //feProject.setCreateTime(new Date());
            feProject.setUser(new FeUser(Integer.valueOf(String.valueOf(session.getAttribute("userid")))));

            FeStyle feStyleCommon = new FeStyle();
            feStyleCommon.setName("common.css");
            feStyleCommon.setCode("");
            FeStyle feStyleReset = new FeStyle();
            feStyleReset.setName("reset.css");
            feStyleReset.setCode("");
            feProject.addStyle(feStyleCommon);
            feProject.addStyle(feStyleReset);

            FeTree feTree = new FeTree();
            feTree.setName(feProjectDomain.getName());
            feTree.setIsFolder("1");
            feTree.setIconSkin("folder");
            List<FeTree> feTreeList = new ArrayList<>(1);
            feTreeList.add(feTree);
            feProject.setTrees(feTreeList);

            feProject = iFeProjectService.save(feProject);

            model.addAttribute("id", feProject.getId());
        }
        model.addAttribute("success", !project.exists());
        model.addAttribute("msg", !project.exists() ? "保存成功" : "项目名称不能重复");
        return "redirect:newProject";
    }

    @RequestMapping(value = "deleteProject", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> projectDelete(@RequestParam("id") int id, HttpSession session) {
        FeProject feProject = iFeProjectService.findOne(id);
        if (null != feProject) {
            String projectPath = environment.getProperty("file.upload.path") + File.separator + feProject.getUser().getMail()
                    + File.separator + feProject.getName();
            deleteFile(projectPath);
            new File(projectPath).delete();
        }
        iFeProjectService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = "updateProject", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@ModelAttribute FeProjectDomain feProjectDomain, BindingResult bindingResult, HttpSession session) {
        FeProject feProject = new FeProject();
        feProject.setId(feProjectDomain.getId());
        feProject.setRemark(feProjectDomain.getRemark());
        feProject.setName(feProjectDomain.getName());
        feProject.setUser(new FeUser(Integer.valueOf(String.valueOf(session.getAttribute("userid")))));
        iFeProjectService.updateById(feProject);
        return MapUtil.getUpdateSuccessMap();
    }

    @RequestMapping(value = "tree", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getTreeByProjectId(@RequestParam("id") String id) {
        FeProject feProject = null;
        if (!isNullOrEmpty(id) && isNumber(id)) {
            feProject = iFeProjectService.findOne(Integer.valueOf(id));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = "{}";
        try {
            jsonStr = objectMapper.writeValueAsString(feProject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        JSONArray jsonArray = ((JSONObject) jsonObject.getJSONArray("trees").get(0)).getJSONArray("trees");

        String mail = feProject.getUser().getMail();
        String projectName = feProject.getName();
        String projectPath = environment.getProperty("file.upload.path")
                + File.separator + mail
                + File.separator + projectName + File.separator + "image";
        String imgURL = "/upload/" + mail + "/" + projectName + "/image/";

        JSONObject jsonObjectImage = JSONObject.fromObject("{}");
        jsonObjectImage.put("name", "image");
        jsonObjectImage.put("iconSkin", "folder");
        jsonObjectImage.put("isFolder", "1");

        final List<File> imageList = new ArrayList<>();

        try {
            searchImage(projectPath, imageList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArrayImage = JSONArray.fromObject("[]");

        for (File file : imageList) {
            if (file.isDirectory()) continue;
            JSONObject obj = JSONObject.fromObject("{}");
            obj.put("name", file.getName());
            obj.put("title", imgURL + file.getName());
            obj.put("iconSkin", "img");
            obj.put("isFolder", "0");
            obj.put("layer", "1");
            jsonArrayImage.add(obj);
        }
        jsonObjectImage.put("trees", jsonArrayImage);
        jsonArray.add(0, jsonObjectImage);

        Map<String, Object> map = new HashMap<>();
        map.put("data", jsonObject);
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "zip")
    public ResponseEntity<byte[]> zip(@RequestParam("id") String id) {
        byte[] result;
        String downloadFileName;
        FeProject feProject = null;
        HttpHeaders headers = new HttpHeaders();

        if (!isNullOrEmpty(id) && isNumber(id))
            feProject = iFeProjectService.findOne(Integer.valueOf(id));
        if (null == feProject) {
            headers.setContentType(MediaType.TEXT_HTML);
            return new ResponseEntity<>("非法操作".getBytes(), headers, HttpStatus.BAD_REQUEST);
        }

        String mail = feProject.getUser().getMail();
        String projectName = feProject.getName();
        String projectPath = environment.getProperty("file.upload.path")
                + File.separator + mail
                + File.separator + projectName;

        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(bos);

            if (new File(projectPath).exists()) {
                final List<File> imageList = new ArrayList<>();

                searchImage(projectPath, imageList);

                ZipEntry zipEntry;
                //zip image
                for (File tmp : imageList) {
                    File parent = tmp.getParentFile();
                    StringBuilder sb = new StringBuilder();
                    while (!mail.equals(tmp.getName()) && null != parent && !mail.equals(parent.getName())) {
                        if (parent.isDirectory())
                            sb.insert(0, parent.getName() + File.separator);
                        parent = parent.getParentFile();
                    }
                    if (tmp.isDirectory()) continue;
                    zipEntry = new ZipEntry(sb.toString() + tmp.getName());
                    zipOutputStream.putNextEntry(zipEntry);
                    Files.copy(tmp, zipOutputStream);
                    zipOutputStream.closeEntry();
                }
            }

            if (feProject.getTrees().size() > 0) {
                packPageToZip(feProject.getTrees().get(0), zipOutputStream, "");
            }

            packCSStoZip(feProject.getStyles(), zipOutputStream, projectName);

            zipOutputStream.close();
            result = bos.toByteArray();
            bos.close();
            downloadFileName = new String((projectName + ".zip").getBytes("gb2312"), "iso-8859-1");
        } catch (IOException ex) {
            ex.printStackTrace();
            headers.setContentType(MediaType.TEXT_HTML);
            return new ResponseEntity<>("服务器错误".getBytes(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", downloadFileName);
        return new ResponseEntity<>(result, headers, HttpStatus.CREATED);
    }

    private void searchImage(String searchPath, List<File> fileList) throws IOException {
        SimpleFileVisitor<Path> finder = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                fileList.add(file.toFile());
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                fileList.add(dir.toFile());
                return FileVisitResult.CONTINUE;
            }
        };

        java.nio.file.Files.walkFileTree(Paths.get(searchPath), finder);
    }

    private void packPageToZip(FeTree feTree, ZipOutputStream zipOutputStream, String path) {
        if (feTree.getIsFolder().equals("1")) {
            path = path + feTree.getName() + File.separator;
            try {
                ZipEntry zipEntry = new ZipEntry(path);
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                ZipEntry zipEntry = new ZipEntry(path + feTree.getName());//+ ".html"
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(feTree.getPages().get(0).getCode().getBytes(Charset.defaultCharset()));
                zipOutputStream.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (feTree.getTrees().size() > 0) {
            for (FeTree tree : feTree.getTrees()) {
                packPageToZip(tree, zipOutputStream, path);
            }
        }
    }

    private void packCSStoZip(List<FeStyle> feStyleList, ZipOutputStream zipOutputStream, String root) {
        if (feStyleList.size() > 0) {
            for (FeStyle feStyle : feStyleList) {
                try {
                    ZipEntry zipEntry = new ZipEntry(root + "/css/" + feStyle.getName());
                    zipOutputStream.putNextEntry(zipEntry);
                    zipOutputStream.write(feStyle.getCode().getBytes(Charset.defaultCharset()));
                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void deleteFile(String filepath) {
        File file = new File(filepath);
        if (file.exists() && file.isDirectory()) {
            if (file.listFiles().length == 0) {
                file.delete();
            } else {
                File delFile[] = file.listFiles();
                int i = file.listFiles().length;
                for (int j = 0; j < i; j++) {
                    try {
                        if (delFile[j].isDirectory()) {
                            deleteFile(delFile[j].getAbsolutePath());
                        }
                        delFile[j].delete();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}
