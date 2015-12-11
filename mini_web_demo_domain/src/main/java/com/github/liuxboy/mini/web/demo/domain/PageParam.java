package com.github.liuxboy.mini.web.demo.domain;

import java.util.HashMap;
import java.util.Map;

public class PageParam {
    protected final static int PAGE_SHOW_COUNT = 10;
    //第多少页
    protected int pageNum = 1;
    //每页多少条
    protected int pageSize = 20;
     //总条数
     protected int totalCount ;
    //targetType: navTab或dialog，用来标记是navTab上的分页还是dialog上的分页
    protected String targetType = "navTab";

    //将分页参数添加至搜索参数
    public Map<String,String> addPage2Map(Map<String,String> map){
        if(null == map){
            map = new HashMap<String,String>();
        }
        //分页计算
        int pageStartNum = 0;
        pageStartNum = (pageNum - 1) * pageSize;
        map.put("page", " limit " + String.valueOf(pageStartNum) + "," + String.valueOf(pageSize));
        return map;
    }

    public static int getPageShowCount() {
        return PAGE_SHOW_COUNT;
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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getNewsTypes() {
        return targetType;
    }

    public void setNewsTypes(String newsTypes) {
        this.targetType = newsTypes;
    }

    public int getNumPerPage() {
        return pageSize;
    }

    public void setNumPerPage(int pageSize) {
        this.pageSize = pageSize;
    }
}
