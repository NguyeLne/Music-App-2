package com.example.mymusic2.model.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseObject<T> implements Serializable {
    private List<T> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public List<T> getContent() {
        return content;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return last;
    }
}
