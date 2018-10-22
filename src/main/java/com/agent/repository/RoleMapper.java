package com.agent.repository;

import com.agent.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {

    Role  findOne(@Param("roleId") int roleId);
}
