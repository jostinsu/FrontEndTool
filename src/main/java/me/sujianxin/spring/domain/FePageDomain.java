package me.sujianxin.spring.domain;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/8
 * <p>Time: 23:02
 * <p>Version: 1.0
 */
public class FePageDomain {
    int id;
    String code;
    String style;
    int treeid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getTreeid() {
        return treeid;
    }

    public void setTreeid(int treeid) {
        this.treeid = treeid;
    }
}
