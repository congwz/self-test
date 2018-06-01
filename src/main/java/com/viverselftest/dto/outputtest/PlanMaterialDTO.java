package com.viverselftest.dto.outputtest;

import lombok.Data;

import java.util.List;

@Data
public class PlanMaterialDTO {

    //模块id
    private String plan_module_id;

    //模块名称
    private String module_name;

    //物料描述
    private String material_desc;

    //物料类型 C-定制件，S-标准件
    private String material_type;

    //品牌
    private String brand;

    //申请日期
    private String request_date;

    //需求日期
    private String require_date;

    //需求料号数量
    private Double require_part_num;

    //需求物料数量
    private Double require_material_num;

    //履行周期
    private Double perform_day;

    //生产周期
    private Double cycle_day;

    //含长交期 T-打开，F-关闭
    private String islong_time;

    //开发新供应商 T-打开，F-关闭
    private String isnew_supplier;

    //计划状态 T-打开，F-关闭
    private String isplan_status;


    //材料id
    //@JsonIgnore
    private String plan_material_id;

    //附件信息
    private List<HandlePlanAttachDTO> attach_list;

    /*@Override
    public String toString() {
        return "PlanMaterialDTO{" +
                "plan_module_id='" + plan_module_id + '\'' +
                ", module_name='" + module_name + '\'' +
                ", material_des='" + material_desc + '\'' +
                ", material_type='" + material_type + '\'' +
                ", brand='" + brand + '\'' +
                ", request_date='" + request_date + '\'' +
                ", require_date='" + require_date + '\'' +
                ", require_part_num=" + require_part_num +
                ", require_material_num=" + require_material_num +
                ", perform_day=" + perform_day +
                ", cycle_day=" + cycle_day +
                ", islong_time='" + islong_time + '\'' +
                ", isnew_supplier='" + isnew_supplier + '\'' +
                ", isplan_status='" + isplan_status + '\'' +
                ", plan_material_id='" + plan_material_id + '\'' +
                ", attach_list=" + attach_list == null ? null : attach_list.toString()+
                '}';
    }*/
}
