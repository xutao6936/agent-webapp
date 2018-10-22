package com.agent.page;

import java.io.Serializable;
import java.util.List;

/**
 * @author lixia
 * Created by lixia on 2016/5/10.
 */
public class Page<T> implements Serializable {

    /**
     * 当前页数
     */
    private int pageNum = 1;

    /**
     * 每页显示条数
     */
    private int pageSize = 10;

    /**
     * 开始条数
     */
    private int startRow;

    /**
     * 结束条数
     */
    private int endRow;

    /**
     * 数据总数
     */
    private int total;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 需要分页的原始对象信息
     */
    private T entity;

    /**
     * 数据结果
     */
    private List<T> result;

    /**
     * 数据合计行
     */
    private List<T> footer;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 分页相关是否已完成
     */
    private boolean completed;

    /**
     * 是否需要统计总数
     */
    private boolean countTotal;

    public Page() {
        this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
        this.endRow = pageNum * pageSize;
        this.countTotal = true;
    }

    public Page(boolean countTotal) {
        this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
        this.endRow = pageNum * pageSize;
        this.countTotal = countTotal;
    }

    public Page(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
        this.endRow = pageNum * pageSize;
        this.countTotal = true;
    }

    public Page(int pageNum, int pageSize, boolean countTotal) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
        this.endRow = pageNum * pageSize;
        this.countTotal = countTotal;
    }

    public Page(int pageNum, int pageSize, boolean countTotal, T entity) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
        this.endRow = pageNum * pageSize;
        this.countTotal = countTotal;
        this.entity = entity;
    }

    public List<T> getFooter() {
        return footer;
    }

    public void setFooter(List<T> footer) {
        this.footer = footer;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCountTotal() {
        return countTotal;
    }

    public void setCountTotal(boolean countTotal) {
        this.countTotal = countTotal;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", total=" + total +
                ", pages=" + pages +
                '}';
    }
}
