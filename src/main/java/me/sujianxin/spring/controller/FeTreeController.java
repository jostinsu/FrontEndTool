package me.sujianxin.spring.controller;

import com.google.common.io.Files;
import me.sujianxin.persistence.model.FeTree;
import me.sujianxin.persistence.service.IFeTreeService;
import me.sujianxin.spring.domain.FeTreeDomain;
import me.sujianxin.spring.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String save(@ModelAttribute FeTreeDomain feTreeDomain) {
        FeTree feTree = new FeTree();
        feTree.setName(feTreeDomain.getName());
        feTree.setIsFolder(feTreeDomain.getIsFolder());
        feTree.setLayer(feTreeDomain.getLayer());
        if (0 != feTreeDomain.getParentid()) {
            feTree.setTree(new FeTree(feTreeDomain.getParentid()));
        }
        iFeTreeService.save(feTree);
        return "";
    }

    @RequestMapping(value = "deleteTree", method = RequestMethod.DELETE)
    public Map<String, Object> delete(@RequestParam("id") int id) {
        iFeTreeService.deleteById(id);
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
        iFeTreeService.updateById(feTree);
        return MapUtil.updateMap();
    }

    @RequestMapping(value = "tree/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findOne(@PathVariable("id") int id) {
        FeTree feTree = iFeTreeService.findOne(id);
        Map<String, Object> map = new HashMap<>(3);
        map.put("success", null != feTree ? true : false);
        map.put("msg", null != feTree ? "" : "非法操作");
        map.put("data", null != feTree ? feTree : "");
        return map;
    }

    @RequestMapping(value = "trees", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAll() {
        List<FeTree> feTree = iFeTreeService.findAll();
        Map<String, Object> map = new HashMap<>(3);
        map.put("success", !feTree.isEmpty() ? true : false);
        map.put("msg", !feTree.isEmpty() ? "" : "非法操作");
        map.put("data", !feTree.isEmpty() ? feTree : "");
        return map;
    }

    @RequestMapping(value = "zip")
    public ResponseEntity<byte[]> zip(@RequestParam("projectid") String projectid) {
        FeTree feTree = null;
        HttpHeaders headers = new HttpHeaders();

        if (org.apache.commons.lang3.math.NumberUtils.isNumber(projectid))
            feTree = iFeTreeService.findOneByProjectId(1);
        if (null == feTree || feTree.getProjects().size() == 0) {
            headers.setContentType(MediaType.TEXT_HTML);
            return new ResponseEntity<>("非法操作".getBytes(), headers, HttpStatus.BAD_REQUEST);
        }

        String mail = feTree.getProjects().get(0).getUser().getMail();
        String projectName = feTree.getProjects().get(0).getName();
        String projectPath = environment.getProperty("file.upload.path")
                + File.separator + mail
                + File.separator + projectName;

        //遍历目录下文件夹及文件
        final List<File> imagesAndCss = new ArrayList<>();
        SimpleFileVisitor<Path> finder = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                imagesAndCss.add(file.toFile());
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                imagesAndCss.add(dir.toFile());
                return FileVisitResult.CONTINUE;
            }
        };

        byte[] result;
        String downloadFileName;

        try {
            java.nio.file.Files.walkFileTree(Paths.get(projectPath), finder);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(bos);

            ZipEntry zipEntry;

            //zip image css
            for (File tmp : imagesAndCss) {
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
            packToZip(feTree, zipOutputStream, "");
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

    private void packToZip(FeTree feTree, ZipOutputStream zipOutputStream, String path) throws IOException {
        if (feTree.getIsFolder().equals("1")) {
            path = path + feTree.getName() + File.separator;
        } else {
            ZipEntry zipEntry = new ZipEntry(path + feTree.getName() + ".html");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(feTree.getPages().get(0).getCode().getBytes(Charset.defaultCharset()));
            zipOutputStream.closeEntry();
        }
        if (feTree.getTrees().size() > 0) {
            for (FeTree tree : feTree.getTrees()) {
                packToZip(tree, zipOutputStream, path);
            }
        }
    }
}
