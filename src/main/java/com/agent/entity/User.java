package com.agent.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class User {

    private int id;
    private String userName;
    private String password;
    private int deptId;
    private int roleId;
    private Boolean enabled;
    private String description;
    private String telphone;
    private int age;
    private String sex;
    private Date createTime;
    private Date updateTime;
    private String creator;
    private String roleName;



}
