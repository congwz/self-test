package com.viverselftest.dto.inquireonline;

import lombok.Data;

import java.util.List;

/**
 * Created by Congwz on 2018/7/17.
 */
@Data
public class hcSupplierQuotedGenDTO {

    //报价单号
    private String qpnumber;

    //询价公司编号
    private String qpcompanyCode;

    //询价公司名称
    private String qpcompanyName;

    //发运至编号
    private String qpsendAddressCode;

    //发运至地址
    private String qpsendAddress;

    //币种
    private String qpcurrency;

    //询价发起人名字
    private String qporiginator;

    //询价时间
    private String qporiginalDate;

    //报价截止时间
    private String qpendDate;

    //分部编号
    private String qpbranchCode;

    //物料类别 S-标准件 C-定制件
    private String qpmaterialType;

    //紧急程度  01-紧急  02-正常
    private String qpurgent;


    //选择的发送报价供应商
    private List<String> suppliersCode;

    //报价单明细
    private List<hcSupplierQuotedGenDetailDTO> details;
}
