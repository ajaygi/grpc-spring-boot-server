package com.kiwi.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "m_roles")
public class Roles implements Serializable  {
    private static final long serialVersionUID = 1493854724975437748L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
