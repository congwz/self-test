package com.viverselftest.service;

import com.viverselftest.dto.PageDTO;

/**
 * Created by Congwz on 2018/11/12.
 */
public interface LostService {

    /**
     * 查询寻物分页信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageDTO getLostList(int pageNumber, int pageSize);
}
