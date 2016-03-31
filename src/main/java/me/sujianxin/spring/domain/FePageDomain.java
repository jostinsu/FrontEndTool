package me.sujianxin.spring.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/8
 * <p>Time: 23:02
 * <p>Version: 1.0
 */
public class FePageDomain {
    private int id;
    private String downloadCode;
    private String mutipleCode;
    private String simpleCode;
    private int treeid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDownloadCode() {
        return downloadCode;
    }

    public void setDownloadCode(String downloadCode) {
        this.downloadCode = downloadCode;
    }

    public String getMutipleCode() {
        return mutipleCode;
    }

    public void setMutipleCode(String mutipleCode) {
        this.mutipleCode = mutipleCode;
    }

    public String getSimpleCode() {
        return simpleCode;
    }

    public void setSimpleCode(String simpleCode) {
        this.simpleCode = simpleCode;
    }

    public int getTreeid() {
        return treeid;
    }

    public void setTreeid(int treeid) {
        this.treeid = treeid;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }

}
