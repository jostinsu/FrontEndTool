package me.sujianxin.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the tree database table.
 */
@Entity
@Table(name = "tree")
@NamedQuery(name = "FeTree.findAll", query = "SELECT f FROM FeTree f")
public class FeTree implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "enum")
    private String isFolder;

    private String name;

    @Column(columnDefinition = "char")
    private String iconSkin;

    //bi-directional many-to-one association to FePage
    @JsonManagedReference
    @OneToMany(mappedBy = "tree", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<FePage> pages = new ArrayList<>();

    //bi-directional many-to-many association to FeProject
    @JsonBackReference
    @ManyToMany(mappedBy = "trees")
//    @JoinTable(
//            name = "connect"
//            , joinColumns = {
//            @JoinColumn(name = "tree_id")
//    }
//            , inverseJoinColumns = {
//            @JoinColumn(name = "project_id")
//    }
//    )
    private List<FeProject> projects;

    //bi-directional many-to-one association to FeTree
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private FeTree tree;

    //bi-directional many-to-one association to FeTree
    @JsonManagedReference
    @OneToMany(mappedBy = "tree")
    private List<FeTree> trees;

    public FeTree() {
    }

    public FeTree(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsFolder() {
        return this.isFolder;
    }

    public void setIsFolder(String isFolder) {
        this.isFolder = isFolder;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public List<FePage> getPages() {
        return this.pages;
    }

    public void setPages(List<FePage> pages) {
        this.pages = pages;
    }

    public FePage addPage(FePage page) {
        getPages().add(page);
        page.setTree(this);

        return page;
    }

    public FePage removePage(FePage page) {
        getPages().remove(page);
        page.setTree(null);

        return page;
    }

    public List<FeProject> getProjects() {
        return this.projects;
    }

    public void setProjects(List<FeProject> projects) {
        this.projects = projects;
    }

    public FeTree getTree() {
        return this.tree;
    }

    public void setTree(FeTree tree) {
        this.tree = tree;
    }

    public List<FeTree> getTrees() {
        return this.trees;
    }

    public void setTrees(List<FeTree> trees) {
        this.trees = trees;
    }

    public FeTree addTree(FeTree tree) {
        getTrees().add(tree);
        tree.setTree(this);

        return tree;
    }

    public FeTree removeTree(FeTree tree) {
        getTrees().remove(tree);
        tree.setTree(null);

        return tree;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }


}