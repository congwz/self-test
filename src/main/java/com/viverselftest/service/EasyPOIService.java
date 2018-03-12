package com.viverselftest.service;

import com.viverselftest.po.ExcelExportOneToMorePO;
import com.viverselftest.po.ExcelExportSimplePO;

import java.util.List;

public interface EasyPOIService {

    List<ExcelExportSimplePO> findSimpleExcelExportData(String workCode);

    List<ExcelExportOneToMorePO> findOneToMoreExcelExportData();
}
