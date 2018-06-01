package com.viverselftest.dto.outputtest;

import lombok.Data;

import java.util.List;

@Data
public class HandlePlanMaterialDTO {

    //新增：'A',删除：'D',更新：'U'
    private String tag;

    private List<PlanMaterialDTO> materials;

    /*@Override
    public String toString() {
        return "HandlePlanMaterialDTO{" +
                "tag='" + tag + '\'' +
                ", materials=" + materials == null ? null : materials.toString() +
                '}';
    }*/
}
