package me.sujianxin.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user database table.
 */
@Entity
@Table(name = "user")
@NamedQuery(name = "FeUser.findAll", query = "SELECT f FROM FeUser f")
public class FeUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String mail;

    private String nickname;

    private String password;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_time")
    private Date registerTime;

    //bi-directional many-to-one association to FeProject
    @OneToMany(mappedBy = "user")
    private List<FeProject> projects;

    public FeUser(int id) {
        this.id = id;
    }

    public FeUser() {
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterTime() {
        return this.registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FeProject> getProjects() {
        return this.projects;
    }

    public void setProjects(List<FeProject> projects) {
        this.projects = projects;
    }

    public FeProject addProject(FeProject project) {
        getProjects().add(project);
        project.setUser(this);

        return project;
    }

    public FeProject removeProject(FeProject project) {
        getProjects().remove(project);
        project.setUser(null);

        return project;
    }

}