package com.mycompany.chiroSupport.employee;


import com.mycompany.chiroSupport.sample.MD5hash;

import javax.persistence.*;

@Entity
@Table(name="employee",
        uniqueConstraints={@UniqueConstraint(columnNames={"user_id"})})
public class Employee{

    @Id
    @Column(name="user_id", nullable=false, unique=true)
    private String user_id;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="role", nullable=false)
    private int role; // 1-receptionist  2-physiotherapist 3-admin


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
