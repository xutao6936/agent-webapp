package com.agent.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShiroUser {


    private int id;
    private String userName;
    private int age;
    private String telphone;
    private int roleId;
    private String roleName;


}
