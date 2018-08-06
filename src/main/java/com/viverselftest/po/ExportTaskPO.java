package com.viverselftest.po;

import lombok.Data;

/**
 * Created by Haoxu.Mu on 2018/3/12.
 */
@Data
public class ExportTaskPO {
    private String id;

    private String work_code;

    private String user_name;

    private String report_title;

    private String file_name;

    private String file_type;

    private String file_url;

    private String start_time;

    private String end_time;

    private String status;

    private String down_litm;
}
