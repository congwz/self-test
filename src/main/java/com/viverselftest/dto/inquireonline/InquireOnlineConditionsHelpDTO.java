package com.viverselftest.dto.inquireonline;

import lombok.Data;

/**
 * Created by Congwz on 2018/7/12.
 */
@Data
public class InquireOnlineConditionsHelpDTO {

    //排序栏位
    private String sort_colum;
    //排序规则 asc  desc
    private String sort_rule;

    //多选用,隔开
    private String branch_code;
    //标准件'S' 定制件'C'
    private String materiel_type;
    //紧急程度：紧急'01' 正常'02'
    private String urgency;

    //表头查询条件
    private String head_conditions;

    //明细查询条件
    private String detail_conditions;

}
