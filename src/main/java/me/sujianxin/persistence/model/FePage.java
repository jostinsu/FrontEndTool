package me.sujianxin.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the page database table.
 */
@Entity
@Table(name = "page")
@NamedQuery(name = "FePage.findAll", query = "SELECT f FROM FePage f")
public class FePage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @Column(columnDefinition = "longtext")
    private String downloadCode;

    @Column(columnDefinition = "longtext")
    private String multipleCode;

    @JsonIgnore
    @Column(columnDefinition = "longtext")
    private String simpleCode;

    //bi-directional many-to-one association to FeTree
    @JsonBackReference
    @ManyToOne
    private FeTree tree;

    public FePage() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDownloadCode() {
        return this.downloadCode;
    }

    public void setDownloadCode(String downloadCode) {
        this.downloadCode = downloadCode;
    }

    public String getSimpleCode() {
        return simpleCode;
    }

    public void setSimpleCode(String simpleCode) {
        this.simpleCode = simpleCode;
    }

    public String getMultipleCode() {
        return multipleCode;
    }

    public void setMultipleCode(String multipleCode) {
        this.multipleCode = multipleCode;
    }

    public FeTree getTree() {
        return this.tree;
    }

    public void setTree(FeTree tree) {
        this.tree = tree;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }

}