package me.sujianxin.persistence.model;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the style database table.
 * 
 */
@Entity
@Table(name="style")
@NamedQuery(name="FeStyle.findAll", query="SELECT f FROM FeStyle f")
public class FeStyle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition = "longtext")
	private String code;

	//bi-directional many-to-one association to FeProject
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

}