package me.sujianxin.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the type database table.
 */
@Entity
@Table(name = "type")
@NamedQuery(name = "FeType.findAll", query = "SELECT f FROM FeType f")
public class FeType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    //bi-directional many-to-one association to FeElement
    @JsonBackReference
    @OneToMany(mappedBy = "type")
    private List<FeElement> elements;

    public FeType(int id) {
        this.id = id;
    }

    public FeType() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FeElement> getElements() {
        return this.elements;
    }

    public void setElements(List<FeElement> elements) {
        this.elements = elements;
    }

    public FeElement addElement(FeElement element) {
        getElements().add(element);
        element.setType(this);

        return element;
    }

    public FeElement removeElement(FeElement element) {
        getElements().remove(element);
        element.setType(null);

        return element;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }

}