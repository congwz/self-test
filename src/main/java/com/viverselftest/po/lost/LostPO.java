package com.viverselftest.po.lost;

import lombok.Data;

/**
 * Created by Congwz on 2018/11/12.
 */
@Data
public class LostPO {

    //主键
    private String id;

    //失物图片地址
    private String image;

    //失物主题
    private String title;

    //失物内容
    private String content;

    //失物名字
    private String name;

    //失主id
    private String user_id;

    //发布日期
    private String create_date;

    //更新日期
    private String update_date;

    //所属省份
    private String province;

    //所属城市
    private String city;

    //遗失状态 Y-遗失  N-找回
    private String is_lost;

    //酬金
    private Double money;

    //备注
    private String remark;
}
