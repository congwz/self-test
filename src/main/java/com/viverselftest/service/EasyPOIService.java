package com.viverselftest.service;

import com.viverselftest.dto.inquireonline.InquireOnlineConditionsDTO;
import com.viverselftest.po.ExcelExportOneToMorePO;
import com.viverselftest.po.ExcelExportSimplePO;

import java.util.List;

public interface EasyPOIService {

    List<ExcelExportSimplePO> findSimpleExcelExportData(String workCode);

    List<ExcelExportOneToMorePO> findOneToMoreExcelExportData();

    /**
     * 根据查询条件导出报价信息
     * @param search_dto
     * @param work_code
     * @param user_name
     */
    void export(InquireOnlineConditionsDTO search_dto, String work_code, String user_name);
}
