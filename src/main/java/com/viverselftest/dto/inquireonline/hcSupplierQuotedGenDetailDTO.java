package com.viverselftest.dto.inquireonline;

import lombok.Data;

/**
 * Created by Congwz on 2018/7/17.
 */
@Data
public class hcSupplierQuotedGenDetailDTO {

    //分部场所编号
    private String qpddepartmentCode;

    //分部场所名称
    private String qpddepartmentName;

    //报价行号
    private Double qpdfindNum;

    //物料号
    private String qpditemNo;

    //物料名称
    private String qpditemName;

    //需求日期
    private String qpdrequiredDate;

    //物料单位
    private String qpditemUnit;

    //制造商部件号
    private String qpdmanufactureNo;

    //制造商名称
    private String qpdmanufactureName;

    //规格型号
    private String qpdspec;

    //版本
    private String qpdrev;

    //图号
    private String qpddrawNo;

    //数量
    private Double qpdqty;

    //请购单号
    private String qpdrequireBillNo;

    //申请单行号
    private Double qpdrbnfindNum;

    //询价公司编号
    private String qpdcompanyCode;

    //询价公司名称
    private String qpdcompanyName;

    //标准件编号
    private String qpdstandardCode;

    //标准件名称
    private String qpdstandardName;

    //定制件编号
    private String qpdcustomCode;

    //定制件名称
    private String qpdcustomName;

    //所属模块
    private String qpdmodule;

    //是否急件
    private String qpdisUrgent;


}
