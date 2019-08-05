package com.viverselftest.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class ExcelExportOdPO {

    @Excel(name = "序列号",height = 20, width = 40)
    private String xlh;

    @Excel(name  = "物料名称",height = 20, width = 45)
    private String wlmc;

    @Excel(name = "客户名称")
    private String khmc;

    @Excel(name = "料号",width = 30)
    private String lh;

    @Excel(name = "项目名称")
    private String xmmc;
}
