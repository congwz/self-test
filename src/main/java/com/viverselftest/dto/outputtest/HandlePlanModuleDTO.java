package com.viverselftest.dto.outputtest;

import lombok.Data;

import java.util.List;

@Data
public class HandlePlanModuleDTO {

    //新增：'A',删除：'D',更新：'U'
    private String tag;

    private List<PlanModuleDTO> modules;

    /*@Override
    public String toString() {
        return "HandlePlanModuleDTO{" +
                "tag='" + tag + '\'' +
                ", modules=" + modules == null ? null : modules.toString() +
                '}';
    }*/
}
