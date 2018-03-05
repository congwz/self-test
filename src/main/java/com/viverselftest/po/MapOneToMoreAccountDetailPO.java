package com.viverselftest.po;

import lombok.Data;

@Data
public class MapOneToMoreAccountDetailPO {

    //对账单单号
    private String r_number;

    //采购单主键
    private String payc_id;

    //明细删除标志
    private String r_del;

    //采购单付款条件编号
    private String pay_condition;

    //采购单付款条件描述
    private String des;
}
