package com.viverselftest.po;

import lombok.Data;

/**
 * Created by Congwz on 2018/8/10.
 */
@Data
public class InquireOnlineDProviderExcelPO {

    //供应商编号
    private String QPSLSupplierCode;

    //供应商名称
    private String provider_name;


    //供应商编号与名称拼接
    private String supplier;


    //承诺交期
    private String QPDSLAcceptDate;

    //未税单价
    private Double QPDSLUnTaxMoney;


    //报价状态(OR单针对供应商的状态)
    private String QPDSLStatus;


}
