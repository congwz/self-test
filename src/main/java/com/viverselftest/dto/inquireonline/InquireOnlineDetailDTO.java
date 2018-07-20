package com.viverselftest.dto.inquireonline;

import lombok.Data;

/**
 * Created by Congwz on 2018/7/9.
 */
@Data
public class InquireOnlineDetailDTO {

    //报价单号
    private String QPNumber;

    //分部场所编号
    private String QPDDepartmentCode;

    //分部场所名称
    private String QPDDepartmentName;

    //报价行号
    private Double QPDFindNum;

    //物料号
    private String QPDItemNo;

    //物料名称
    private String QPDItemName;

    //需求日期
    private String QPDRequiredDate;

    //物料单位
    private String QPDItemUnit;

    //制造商部件号
    private String QPDManufactureNo;

    //制造商名称
    private String QPDManufactureName;

    //规格型号
    private String QPDSpec;

    //版本
    private String QPDRev;

    //图号
    private String QPDDrawNo;

    //数量
    private Double QPDQty;

    //请购单号
    private String QPDRequireBillNo;

    //申请单行号
    private Double QPDRBNFindNum;

    //询价公司编号
    private String QPDCompanyCode;

    //询价公司名称
    private String QPDCompanyName;

    //标准件编号
    private String QPDStandardCode;

    //定制件编号
    private String QPDCustomCode;

    //所属模块
    private String QPDModule;

    //是否急件
    private String QPDIsUrgent;


}
