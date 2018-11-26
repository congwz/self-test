package com.viverselftest.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.viverselftest.dao.jde.LostMapper;
import com.viverselftest.dto.PageDTO;
import com.viverselftest.exception.ErrorException;
import com.viverselftest.po.lost.LostPO;
import com.viverselftest.po.lost.UserInfoPO;
import com.viverselftest.service.LostService;
import com.viverselftest.util.CryptUtils;
import com.viverselftest.util.ErrorMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Congwz on 2018/11/12.
 */
@Service
public class LostServiceImpl implements LostService {

    @Autowired
    private LostMapper lostMapper;

    @Autowired
    private ErrorMsgUtils errorMsgUtils;

    @Autowired
    public CryptUtils crptUtill;


    /**
     * 查询寻物分页信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageDTO getLostList(int pageNumber, int pageSize) {
        PageDTO pageDTO = new PageDTO();

        PageHelper.startPage(pageNumber,pageSize);
        List<LostPO> data = lostMapper.findLostList();
        pageDTO.setPageNumber(pageNumber);
        pageDTO.setPageSize(pageSize);
        pageDTO.setData(data);
        pageDTO.setTotal((int) new PageInfo<>(data).getTotal());

        return pageDTO;
    }

    /**
     * 注册
     * @param user
     */
    @Override
    public void addUserRegisterInfo(UserInfoPO user) {

        //检查用户是否已注册过
        int isRegisterAccount = lostMapper.findUserIsRegisterCount(user);
        if(isRegisterAccount > 0) {
            throw new ErrorException("200000", errorMsgUtils.errorMsg("", "200000"));
        }

        //Cipher加密
        user.setPassword(crptUtill.encrypt(user.getPassword()));
        user.setId(UUID.randomUUID().toString());

        lostMapper.addRegisterInfo(user);
    }

    /**
     * 验证用户名是否已存在
     * @param account
     * @return
     */
    @Override
    public boolean verfityAccount(String account) {
        int count = lostMapper.findUserIsRegister(account);
        if(count > 0) {
            return true;
        }
        return false;
    }
}
