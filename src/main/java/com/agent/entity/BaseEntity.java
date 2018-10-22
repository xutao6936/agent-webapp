package com.agent.entity;

import com.agent.util.FieldUtil;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import java.lang.reflect.Field;

public class BaseEntity {

    /**
     * 顺序
     */
    private String sort;

    /**
     * 排序
      */
    private String order;

    public BaseEntity() {
    }

    public String getSort() {
        if(!StringUtils.isEmpty(sort)){
            Field field = FieldUtil.getFieldByName(this.getClass(), sort);
            if(field != null){
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    String name = column.name();
                    if (!StringUtils.isEmpty(name)) {
                        return name;
                    }
                }
            }
        }

        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
