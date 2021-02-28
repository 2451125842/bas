package com.nju.graduation.project.bas.domain.vo;

import lombok.ToString;

import java.util.List;

/**
 * @author shanhe
 * @className PageVO
 * @date 2021-02-27 16:52
 **/
@ToString
public class PageVO <T> {
    private List<T> list;
    private int pageNum;//当前页面
    private int total;//页面总素

    public List<T> getList() {
        return list;
    }

    public PageVO<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    public int getPageNum() {
        return pageNum;
    }

    public PageVO<T> setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public PageVO<T> setTotal(int total) {
        this.total = total;
        return this;
    }
}
