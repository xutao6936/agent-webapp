package com.agent.service.impl;

import com.agent.entity.Role;
import com.agent.entity.User;
import com.agent.repository.RoleMapper;
import com.agent.repository.UserMapper;
import com.agent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;
    @Override
    public User findOne(Map<String, String> map) {
        List<User> users = userMapper.findOne(map);
        if(!CollectionUtils.isEmpty(users)){
            User user = users.get(0);
            Role role = roleMapper.findOne(user.getRoleId());
            user.setRoleName((role !=null&& role.getEnabled()) ?role.getRoleName():null);
            return user;
        }
        return null;
    }
}
