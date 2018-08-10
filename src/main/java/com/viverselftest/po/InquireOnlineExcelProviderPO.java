package com.viverselftest.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * Created by Congwz on 2018/7/12.
 */
@Data
public class InquireOnlineExcelProviderPO {


    //截止时间 精确到时
    @Excel(name = "供应商报价截止时间", width = 50)
    private String QPEndDate;

    //报价状态 进行中 结束 取消 终止 已过期
    @Excel(name = "报价状态", width = 25)
    private String QPStatus;

    //询价日期
    @Excel(name = "询价日期", width = 25)
    private String QPOriginalDate;

    //公司编号
    private String QPCompanyCode;

    //公司名称
    private String QPCompanyName;

    //分部编号
    private String QPBranchCode;

    //分部名称
    @Excel(name = "所属分部")
    private String QPBranchName;

    //询价人工号
    private String QPCreateBy;

    //询价人名字
    @Excel(name = "询价人")
    private String QPOriginator;



    //公司编号与名称拼接
    @Excel(name = "公司", width = 80)
    private String QPCompany;


    //供应商编号
    private String QPSLSupplierCode;

    //供应商名称
    @Excel(name = "供应商名称", width = 15)
    private String provider_name;

    //报价未税总金额
    @Excel(name = "报价未税总金额")
    private Double quotedprice_total;

    //是否存在不报价  不报价: '0'
    private String qpdslisquoted;



    //excel显示标识和供应商编号的字段
    @Excel(name = "供应商编号", width = 15)
    private String quoted_tag;

}
