package com.viverselftest.po.lost;

import lombok.Data;

/**
 * Created by Congwz on 2018/11/20.
 */
@Data
public class UserInfoPO {

    //主键
    private String id;

    //账号
    private String account;

    //密码
    private String password;

    //邮箱
    private String email;

    //qq
    private String qq;

    //phone
    private String phone;

    //年龄  不传值-1
    private Integer age;

    //性别 0-女 1-男  不传值-1
    private Integer sex;

    //创建时间
    private String create_date;

    //是否失效 否-N 是-Y
    private String is_delete;
}
