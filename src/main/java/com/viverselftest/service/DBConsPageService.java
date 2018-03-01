package com.viverselftest.service;

import com.github.pagehelper.PageInfo;
import com.viverselftest.po.DBConsPagePO;

import java.util.List;

public interface DBConsPageService {

    int getCountInfo();

    List<DBConsPagePO> getInfo(String workCode);
    PageInfo getPageInfo(String workCode,int pageNumber,int pageSize);
}
