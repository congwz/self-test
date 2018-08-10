package com.viverselftest.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Haoxu.Mu on 2017/8/15.
 */
@Data
public class PageDTO<T> {

    private int pageNumber;
    private int pageSize;
    private int total;
    private List<T> data;
}
