package com.viverselftest.dto.inquireonline;

import lombok.Data;

/**
 * Created by Congwz on 2018/7/12.
 */
@Data
public class InquireOnlineConditionsHDTO {

    //报价单号
    private String QPNumber;

    //截至时间 精确到时
    private String QPEndDate;

    //报价状态 进行中 结束 取消 终止 已过期
    private String QPStatus;

    //询价日期
    private String QPOriginalDate;

    //公司编号
    private String QPCompanyCode;

    //公司名称
    private String QPCompanyName;

    //分部编号
    private String QPBranchCode;

    //分部名称
    private String QPBranchName;

    //询价人工号
    private String QPCreateBy;

    //询价人名字
    private String QPOriginator;

    //供应商编号
    private String QPSLSupplierCode;

    //供应商名称
    private String provider_name;

    //报价总金额
    private String quotedprice_total;
}
