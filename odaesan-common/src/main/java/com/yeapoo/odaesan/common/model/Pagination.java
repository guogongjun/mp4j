package com.yeapoo.odaesan.common.model;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Pagination {

    private int index;
    private int size;
    private int count;
    @JsonIgnore
    private int offset;

    public Pagination(int index, int size) {
        this.index = index;
        if (size > 100) {
            size = 100; // 限制每次获得的数量 XXX remove hardcode
        }
        this.size = size;
        this.offset = (index - 1) * size;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
