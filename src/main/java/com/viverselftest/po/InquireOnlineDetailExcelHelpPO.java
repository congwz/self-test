package com.viverselftest.po;

import lombok.Data;

import java.util.List;

/**
 * Created by Congwz on 2018/7/12.
 */
@Data
public class InquireOnlineDetailExcelHelpPO {

    //报价单号
    private String QPNumber;



    //供应商编号
    private String QPSLSupplierCode;

    //供应商名称
    private String provider_name;

    //供应商编号与名称拼接
    private String supplier;



    //明细信息
    private List<InquireOnlineDetailHelpPO> details;


}
