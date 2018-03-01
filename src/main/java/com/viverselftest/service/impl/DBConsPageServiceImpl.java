package com.viverselftest.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.viverselftest.dao.jde.DBConsPageMapper;
import com.viverselftest.po.DBConsPagePO;
import com.viverselftest.service.DBConsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBConsPageServiceImpl implements DBConsPageService {

    @Autowired
    private DBConsPageMapper dbConsPageMapper;

    @Override
    public int getCountInfo() {
        return dbConsPageMapper.findCountInfo();
    }

    @Override
    public List<DBConsPagePO> getInfo(String workCode) {
        return dbConsPageMapper.findInfo(workCode);
    }

    @Override
    public PageInfo getPageInfo(String workCode, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        PageHelper.orderBy("display_code desc"); /*正确的排序*/
        List<DBConsPagePO> resList = dbConsPageMapper.findInfo(workCode);
        PageInfo pageInfo = new PageInfo(resList);
        //PageInfo pageInfo = new PageInfo(); /*其他有些字段会没有值*/
        pageInfo.setList(resList);
        //pageInfo.setOrderBy("create_time desc"); /*无效的排序*/
        return pageInfo;
    }
}
