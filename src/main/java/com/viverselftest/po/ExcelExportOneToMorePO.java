package com.viverselftest.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.Data;

import java.util.List;

@Data
public class ExcelExportOneToMorePO {

    private String user_id;

    @Excel(name = "用户名",width = 20, needMerge = true)
    private String username;

    @ExcelEntity(name = "所属部门")
    private ExcelExportOneToMorePartPO department;

    @ExcelCollection(name = "管理类型")
    private List<ExcelExportSimplePO> display;
}
