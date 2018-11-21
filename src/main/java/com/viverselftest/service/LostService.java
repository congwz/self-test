package com.viverselftest.service;

import com.viverselftest.dto.PageDTO;
import com.viverselftest.po.lost.UserInfoPO;

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

    /**
     * 注册
     * @param user
     */
    void addUserRegisterInfo(UserInfoPO user);
}
