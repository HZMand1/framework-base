package com.base.vo;

import java.io.Serializable;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/15 11:03
 * @copyright XXX Copyright (c) 2019
 */
public class IfQuery implements Serializable {
    private static final long serialVersionUID = -8591771048710109350L;
    private int start;
    private int pageSize;
    private int offset;
    private int limit;

    public IfQuery() {
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
        this.offset = this.start;
    }

    public int getPageSize() {
        return this.pageSize <= 0 ? 10 : this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.limit = pageSize;
    }
}