package com.agent.repository;

import com.agent.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {

    List<User> findOne(Map<String,String> map);

}
