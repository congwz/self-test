package com.viverselftest.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.Data;

@Data
public class ExcelExportSimplePO {

    //private String rn;
    private String display_id;

    @Excel(name = "工号",height = 20, width = 30)
    private String work_code;

    @Excel(name  = "类型编号")
    private String display_code;

    @Excel(name = "类型名称",height = 20, width = 50)
    private String display_name;

    @Excel(name = "类型",width = 30)
    private String display_classify;

    @Excel(name = "创建日期",format = "yyyy-MM-dd")
    private String create_time;

    @Excel(name = "用户名")
    @ExcelIgnore
    private String user_name;
}
