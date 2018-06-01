package com.viverselftest.dto.outputtest;

import lombok.Data;

import java.util.List;

@Data
public class HandlePlanAttachDTO {

    //新增：'A',删除：'D',更新：'U'
    private String tag;

    private List<PlanAttachDTO> attachs;

    @Override
    public String toString() {
        return "HandlePlanAttachDTO{" +
                "tag='" + tag + '\'' +
                ", attachs=" + attachs == null ? null : attachs.toString() +
                '}';
    }
}
