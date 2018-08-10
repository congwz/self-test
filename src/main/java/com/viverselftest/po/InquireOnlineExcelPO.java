package com.viverselftest.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;

import java.util.List;

/**
 * Created by Congwz on 2018/8/9.
 */
@Data
public class InquireOnlineExcelPO {

    //报价单号
    @Excel(name = "报价单号",width = 20, needMerge = true)
    private String QPNumber;

    //供应商相关
    @ExcelCollection(name = "")
    private List<InquireOnlineExcelProviderPO> detail;
}
