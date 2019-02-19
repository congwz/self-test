package com.viverselftest.po.elasticsearch;

import lombok.Data;

import java.util.Date;

/**
 * Created by Congwz on 2019/2/11.
 */
@Data
public class EsLFPO {

    private String id;

    //标题
    private String title;

    private String url;

    //内容
    private String content;

    //地址
    private String addr;

    private String time;

    //酬金
    private String money;

    //得分
    private Double score;

    //姓名
    private String name;




    //总数量
    private Integer total;

    //总用时
    private Float searchTime;


}
