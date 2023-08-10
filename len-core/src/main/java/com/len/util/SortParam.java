package com.len.util;

public class SortParam {
    private String sortBy;
    private boolean asc = true;

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public SortParam(String sortBy, boolean asc) {
        this.sortBy = sortBy;
        this.asc = asc;
    }

    public SortParam() {
    }
}