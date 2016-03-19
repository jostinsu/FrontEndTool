package me.sujianxin.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the style database table.
 */
@Entity
@Table(name = "style")
@NamedQuery(name = "FeStyle.findAll", query = "SELECT f FROM FeStyle f")
public class FeStyle implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "char")
    private String name;

    @Column(columnDefinition = "longtext")
    private String code;

    //bi-directional many-to-one association to FeProject
    @JsonBackReference
    @ManyToOne
    private FeProject project;

    public FeStyle() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public FeProject getProject() {
        return this.project;
    }

    public void setProject(FeProject project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }

}