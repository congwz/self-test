package com.viverselftest.service;

import com.viverselftest.dto.PageDTO;
import com.viverselftest.dto.inquireonline.InquireOnlineConditionsDTO;
import com.viverselftest.po.ExcelExportOneToMorePO;
import com.viverselftest.po.ExcelExportSimplePO;
import com.viverselftest.po.InquireOnlineExcelPO;

import java.util.List;

public interface EasyPOIService {

    List<ExcelExportSimplePO> findSimpleExcelExportData(String workCode);

    List<ExcelExportOneToMorePO> findOneToMoreExcelExportData();

    /**
     * 获取报价单表头
     * @param search_dto
     * @return
     */
    PageDTO getQuotedInfoByHD(InquireOnlineConditionsDTO search_dto);

    /**
     * 根据查询条件导出报价信息
     * @param search_dto
     * @param work_code
     * @param user_name
     */
    void export(InquireOnlineConditionsDTO search_dto, String work_code, String user_name);

    /**
     * 导出一对多数据的excel
     * @return
     */
    List<InquireOnlineExcelPO> exportOneToMoreExcel(InquireOnlineConditionsDTO search_dto);






    /**
     * 行转列导出excel明细与供应商
     * @param search_dto
     */
    void exportDetailAndProvider(InquireOnlineConditionsDTO search_dto);
}
