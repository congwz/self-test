package com.viverselftest.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class ExcelExportBigDataPO {

    @Excel(name = "序号")
    private int id;

    @Excel(name = "工号")
    private String work_code;

    @Excel(name = "姓名", width = 30)
    private String user_name;

    @Excel(name = "生日", width = 30)
    private Date birthday;

    @Excel(name = "电话", width = 15)
    private String phone;

    @Excel(name = "备注", width = 40)
    private String userRemark;
}
