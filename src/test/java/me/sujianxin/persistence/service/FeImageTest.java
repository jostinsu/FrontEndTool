package me.sujianxin.persistence.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.File;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/19
 * <p>Time: 10:28
 * <p>Version: 1.0
 */
public class FeImageTest {

    @Test
    public void rename() {
        String path = "D:/apache/front_end/upload/751611201@qq.com/项目一/image";
//        File file=new File(path+"lau.jpg");
        File reFile = new File(path);
//        file.renameTo(reFile);
        System.out.println(reFile.exists());
        String[] children = reFile.list();
        for (int i = 0; i < children.length; i++) {
            System.out.println(children[i]);
        }
//        try {
//            Files.delete(Paths.get(path));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void suffix() {
        String suffix = "JPG,GIF,PNG";
        String name = "lau.jpg";
        System.out.println(name.substring(name.lastIndexOf(".") + 1));
    }

    @Test
    public void json() {
        String jsonStr = "{\n" +
                " \"id\": 23,\n" +
                " \"createTime\": \"2016-03-20\",\n" +
                " \"name\": \"9999\",\n" +
                " \"remark\": \"999999\",\n" +
                " \"styles\":  [\n" +
                "    {\n" +
                "   \"id\": 10,\n" +
                "   \"name\": \"common.css\",\n" +
                "   \"code\": \"\"\n" +
                "  },\n" +
                "    {\n" +
                "   \"id\": 11,\n" +
                "   \"name\": \"reset.css\",\n" +
                "   \"code\": \"\"\n" +
                "  }\n" +
                " ],\n" +
                " \"trees\": [ {\n" +
                "  \"id\": 32,\n" +
                "  \"isFolder\": \"1\",\n" +
                "  \"name\": \"root\",\n" +
                "  \"iconSkin\": null,\n" +
                "  \"pages\": [],\n" +
                "  \"trees\": []\n" +
                " }]\n" +
                "}";
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        JSONArray jsonArray = ((JSONObject) jsonObject.getJSONArray("trees").get(0)).getJSONArray("trees");
        System.out.println(((JSONObject) jsonObject.getJSONArray("trees").get(0)).getJSONArray("trees").size());
    }
}
