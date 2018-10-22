package com.agent.service;

import com.agent.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User findOne(Map<String,String> map);
}
