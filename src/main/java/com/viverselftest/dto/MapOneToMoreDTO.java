package com.viverselftest.dto;

import com.viverselftest.po.MapOneToMoreManagePO;
import lombok.Data;

import java.util.List;

@Data
public class MapOneToMoreDTO {

    private String work_code;

    private String user_name;

    private List<MapOneToMoreManagePO> manage_info;

}
