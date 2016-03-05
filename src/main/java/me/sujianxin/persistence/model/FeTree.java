package me.sujianxin.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the tree database table.
 * 
 */
@Entity
@Table(name="tree")
@NamedQuery(name="FeTree.findAll", query="SELECT f FROM FeTree f")
public class FeTree implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition = "enum")
	private String isFolder;

	private String layer;

	private String name;

	//bi-directional many-to-one association to FePage
	@OneToMany(mappedBy="tree")
	private List<FePage> pages;

	//bi-directional many-to-many association to FeProject
	@ManyToMany
	@JoinTable(
		name="connect"
		, joinColumns={
			@JoinColumn(name="tree_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="project_id")
			}
		)
	private List<FeProject> projects;

	//bi-directional many-to-one association to FeTree
	@ManyToOne
	@JoinColumn(name="parent_id")
	private FeTree tree;

	//bi-directional many-to-one association to FeTree
	@OneToMany(mappedBy="tree")
	private List<FeTree> trees;

	public FeTree() {
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

	public String getLayer() {
		return this.layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

}