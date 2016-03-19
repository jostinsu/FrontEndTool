package me.sujianxin.persistence.service;

import org.junit.Test;

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
        String path = "D:/apache/front_end/upload/751611201@qq.com/项目一/image/";
//        File file=new File(path+"lau.jpg");
//        File reFile=new File(path+"20140816172427.jpg");
//        file.renameTo(reFile);
        System.out.println(path.substring(0, path.lastIndexOf("/") + 1));
    }
}
