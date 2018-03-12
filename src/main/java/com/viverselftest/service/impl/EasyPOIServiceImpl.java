package com.viverselftest.service.impl;

import com.viverselftest.dao.jde.EasyPOIMapper;
import com.viverselftest.po.ExcelExportOneToMorePO;
import com.viverselftest.po.ExcelExportSimplePO;
import com.viverselftest.service.EasyPOIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EasyPOIServiceImpl implements EasyPOIService {

    @Autowired
    private EasyPOIMapper easyPOIMapper;



    /**
     * 最简单的excel导出
     * @param workCode
     * @return
     */
    @Override
    public List<ExcelExportSimplePO> findSimpleExcelExportData(String workCode) {
        return easyPOIMapper.findSimpleExcelExportData(workCode);
    }

    /**
     * 一对多数据的excel导出
     * @return
     */
    @Override
    public List<ExcelExportOneToMorePO> findOneToMoreExcelExportData() {
        return easyPOIMapper.findOneToMoreExcelExportData();
    }
}
