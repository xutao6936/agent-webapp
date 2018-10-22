package com.agent.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class Role {

    private int id;
    private String roleName;
    private Boolean enabled;
    private String description;
    private Date createTime;
    private Date updateTime;
    private String creator;



}
