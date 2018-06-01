package com.viverselftest.dto.outputtest;

import lombok.Data;

@Data
public class PlanAttachDTO {

    //属性信息
    private String attribute_info;

    //描述
    private String des;

    //属性状态
    private String attribute_status;

    //计划完成日期
    private String predict_finish_date;

    //实际完成日期
    private String actual_finish_date;

    //附件信息id
    private String plan_attach_id;
}
