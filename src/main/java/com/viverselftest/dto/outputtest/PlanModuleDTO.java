package com.viverselftest.dto.outputtest;

import lombok.Data;

@Data
public class PlanModuleDTO {

    //模块id
    private String plan_module_id;

    //模块名称
    private String module_name;

    //计划设计完成日期
    private String finish_date;

    //计划组装开始日期
    private String start_date;

    //模块状态 T-打开，F-关闭
    private String module_status;

    //是否冻结 Y-冻结，N-不冻结
    private String isfreeze;

}
