package com.viverselftest.dto.outputtest;

import lombok.Data;

import java.util.List;

@Data
public class HandlePlanDetailDTO {

    //计划设置表头id
    private String plan_head_id;

    //计划状态 1 待计划，2 计划中，-1 已关闭
    private String plan_status;

    //模块计划
    private List<HandlePlanModuleDTO> module_list;

    //物料计划
    private List<HandlePlanMaterialDTO> material_list;


    /*@Override
    public String toString() {
        return "HandlePlanDetailDTO{" +
                "plan_head_id='" + plan_head_id + '\'' +
                ", plan_status='" + plan_status + '\'' +
                ", module_list=" + module_list == null ? null : module_list.toString() +
                ", material_list=" + material_list == null ? null : material_list.toString() +
                '}';
    }*/
}
