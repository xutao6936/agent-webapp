package com.agent.service;

import com.agent.mybatis.interceptor.PageHelper;
import com.agent.repository.SearchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    private static final String BRAND_KEY = "brand";

    private static final String BRAND_NAME = "奔驰";

    @Autowired
    private SearchDao searchDao;

    public String find(String tel){
        List<Map<String, Object>> list = searchDao.find(tel);
        if(list != null && list.size() > 0){
            Map<String, Object> map = list.get(0);
            if(BRAND_NAME.equals(map.get(BRAND_KEY).toString().trim())){
                return "1";
            } else {
                return "2";
            }
        }

        return "3";
    }
}
