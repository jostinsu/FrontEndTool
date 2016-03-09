package me.sujianxin.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the project database table.
 */
@Entity
@Table(name = "project")
@NamedQuery(name = "FeProject.findAll", query = "SELECT f FROM FeProject f")
public class FeProject implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_time")
    private Date createTime;

    private String name;

    private String remark;

    //bi-directional many-to-one association to FeUser
    @ManyToOne
    private FeUser user;

    //bi-directional many-to-one association to FeStyle
    @OneToMany(mappedBy = "project")
    private List<FeStyle> styles;

    //bi-directional many-to-many association to FeTree
    @ManyToMany(mappedBy = "projects")
    private List<FeTree> trees;

    public FeProject() {
    }

    public FeProject(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public FeUser getUser() {
        return this.user;
    }

    public void setUser(FeUser user) {
        this.user = user;
    }

    public List<FeStyle> getStyles() {
        return this.styles;
    }

    public void setStyles(List<FeStyle> styles) {
        this.styles = styles;
    }

    public FeStyle addStyle(FeStyle style) {
        getStyles().add(style);
        style.setProject(this);

        return style;
    }

    public FeStyle removeStyle(FeStyle style) {
        getStyles().remove(style);
        style.setProject(null);

        return style;
    }

    public List<FeTree> getTrees() {
        return this.trees;
    }

    public void setTrees(List<FeTree> trees) {
        this.trees = trees;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }

}