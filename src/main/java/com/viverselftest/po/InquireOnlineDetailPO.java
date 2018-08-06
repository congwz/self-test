package com.viverselftest.po;

import lombok.Data;

/**
 * Created by Congwz on 2018/7/12.
 */
@Data
public class InquireOnlineDetailPO {

    private int rn;

    //供应商编号
    private String QPSLSupplierCode;

    //供应商名称
    private String provider_name;

    //报价单号
    private String QPNumber;

    //报价单行号
    private String QPDFindNum;

    //物料号
    private String QPDItemNo;

    //物料名称
    private String QPDItemName;

    //报价状态(OR单针对供应商的状态)
    private String QPDSLStatus;

    //制造商部件号
    private String QPDManufactureNo;

    //制造商名称
    private String QPDManufactureName;

    //规格型号
    private String QPDSpec;

    //单位
    private String QPDItemUnit;

    //图号
    private String QPDDrawNo;

    //版本
    private String QPDRev;

    //定制件类型编号
    private String QPDCustomCode;

    //定制件名称
    private String QPDCustomName;

    //标准件类型编号
    private String QPDStandardCode;

    //标准件名称
    private String QPDStandardName;

    //所属模块
    private String QPDModule;

    //是否急件
    private String QPDIsUrgent;

    //需求日期
    private String QPDRequiredDate;

    //单据日期(报价单生成日期)
    private String QPCreateDate;

    //报价发起人账号
    private String QPDSLSupplierUserAccount;

    //报价发起人名字
    private String QPDSLSupplierUserName;

    //发运至编号
    private String QPSendAddressCode;

    //发运至名称
    private String QPSendAddress;

    //币别
    private String QPCurrency;

    //供应商邮箱
    private String provider_email;

    //原始单号
    private String QPDRequireBillNo;

    //原始单据类型
    private String ysdhlx;

    //原始单据公司编号
    private String QPDCompanyCode;

    //原始单据公司名称
    private String QPDCompanyName;

    //原始单据行号
    private String QPDRBNFindNum;

    //报价单截止时间
    private String QPEndDate;

    //报价答复日期
    private String QPDAnswerDate;

    //询价日期
    private String QPOriginalDate;

    //询价人名字
    private String QPOriginator;

    //公司编号(表头公司)
    private String QPCompanyCode;

    //公司名称(表头公司)
    private String QPCompanyName;

    //取消时间
    private String QPDQuitDate;

    //终止时间
    private String QPDStopDate;

    //分部场所编号
    private String QPDDepartmentCode;

    //分部场所名称
    private String QPDDepartmentName;

    //采购取消/终止的备注
    private String QPDRBNRemark;

    //承诺交期
    private String QPDSLAcceptDate;

    //未税单价
    private Double QPDSLUnTaxMoney;

    //供应商备注
    private String QPDSLRemark;



    //报价明细行状态  进行中 结束
    private String QPDStatus;


    //供应商编号与名称拼接
    private String supplier;

    //定制件类型编号与名称拼接
    private String QPDCustom;

    //标准件类型编号与名称拼接
    private String QPDStandard;

    //发运至编号与名称拼接
    private String QPSend;

    //原始单据公司编号与名称拼接
    private String QPDCompany;

    //公司编号与名称拼接(表头公司)
    private String QPCompany;




}
