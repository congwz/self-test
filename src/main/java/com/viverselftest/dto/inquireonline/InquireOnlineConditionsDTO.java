package com.viverselftest.dto.inquireonline;

import lombok.Data;

/**
 * Created by Congwz on 2018/7/12.
 */
@Data
public class InquireOnlineConditionsDTO {

    private int pageNumber;
    private int pageSize;
    //排序栏位
    private String sort_colum;
    //排序规则 asc  desc
    private String sort_rule;

    //private String work_code;

    //多选用,隔开
    private String branch_code;
    //标准件'STANDARD' 定制件'CUSTOM'
    private String materiel_type;
    //紧急程度：紧急'01' 正常'02'
    private String urgency;
    //表头'H' 明细'D'
    private String hd_type;

    //表头查询条件
    private InquireOnlineConditionsHDTO head_conditions;

    //明细查询条件
    private InquireOnlineConditionsDDTO detail_conditions;

}
