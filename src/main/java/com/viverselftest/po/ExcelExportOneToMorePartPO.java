package com.viverselftest.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

@Data
public class ExcelExportOneToMorePartPO {

    private int part_id;

    @Excel(name = "部门名称",orderNum = "1",needMerge = true)
    private String part_name;
}
