package com.viverselftest.dto;

import com.viverselftest.po.MapOneToMoreAccountDetailPO;
import lombok.Data;

import java.util.List;

@Data
public class MapOneToMoreAccountDTO {

    //对账单号
    private String r_number;

    //对账类型
    private String r_type;

    //所属公司编号
    private String company;

    //公司名称
    private String gsmc;

    //对账单明细
    private List<MapOneToMoreAccountDetailPO> detail;
}
