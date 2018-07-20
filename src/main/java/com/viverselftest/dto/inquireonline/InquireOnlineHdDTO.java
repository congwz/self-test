package com.viverselftest.dto.inquireonline;

import lombok.Data;

import java.util.List;

/**
 * Created by Congwz on 2018/7/9.
 */
@Data
public class InquireOnlineHdDTO {

    //报价单号
    private String QPNumber;

    //询价公司编号
    private String QPCompanyCode;

    //询价公司名称
    private String QPCompanyName;

    //发运至编号
    private String QPSendAddressCode;

    //币种
    private String QPCurrency;

    //询价发起人工号
    private String QPCreateBy;

    //询价发起人名字
    private String QPOriginator;

    //报价截止时间
    private String QPEndDate;

    //分部编号
    private String QPBranchCode;

    //物料类别 S-标准件 C-定制件
    private String QPMaterialType;

    //紧急程度  01-紧急  02-正常
    private String QPURGENT;


    //选择的发送报价供应商
    private List<InquireOnlineProviderDTO> providers;

    //报价单明细
    private List<InquireOnlineDetailDTO> order_detail;



}
