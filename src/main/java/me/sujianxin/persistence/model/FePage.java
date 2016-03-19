package me.sujianxin.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(columnDefinition = "longtext")
    private String code;
    @Column(columnDefinition = "longtext")
    private String style;

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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
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