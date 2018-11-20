package com.viverselftest.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.viverselftest.dao.jde.LostMapper;
import com.viverselftest.dto.PageDTO;
import com.viverselftest.po.lost.LostPO;
import com.viverselftest.service.LostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Congwz on 2018/11/12.
 */
@Service
public class LostServiceImpl implements LostService {

    @Autowired
    private LostMapper lostMapper;


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


}
