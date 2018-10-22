package com.agent.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SearchDao {

    List<Map<String, Object>> find(@Param("tel") String tel);

}
