package com.viverselftest.dto.inquireonline;

import lombok.Data;

import java.util.List;

/**
 * Created by Congwz on 2018/8/20.
 */
@Data
public class hcSupplierEmailDTO {

    //供应商编号
    private String supplierCode;

    //供应商名称
    private String supplierName;

    //邮件List
    private List<String> userMailAddresses;
}
