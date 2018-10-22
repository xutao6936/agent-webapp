package com.agent.page;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class EasyuiPage<T>  {

    private List<T> rows;
    @Getter
    @Setter
    private long total;


    public EasyuiPage() {
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
