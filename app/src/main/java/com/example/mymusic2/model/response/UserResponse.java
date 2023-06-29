package com.example.mymusic2.model.response;


import java.io.Serializable;
import java.util.List;

public class UserResponse implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;
    private List<Long> roleId;

    public UserResponse() {
    }

    public UserResponse(String name, String email, String username, String password, List<Long> roleId) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }

    public UserResponse(String name, String email, String username, String password, List<Long> roleId, Long id) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getRoleId() {
        return roleId;
    }

    public void setRoleId(List<Long> roleId) {
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
