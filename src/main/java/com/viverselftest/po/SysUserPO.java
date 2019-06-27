package com.viverselftest.po;

import lombok.Data;

@Data
public class SysUserPO {

	//人员id
	private int id;

	//工号
	private String workcode;

	//姓名
	private String username;

	//密码
	private String password;

	//性别 0 男，1 女
	private String sex;

	//生日
	private String birthday;

	//座机号
	private String telphone;

	//手机号
	private String mobile;

	//邮箱
	private String email;

	//部门id
	private int department_id;

	//部门编号
	private String department_code;

	//部门名称
	private String department_name;

	//公司id
	private int company_id;

	//公司编号
	private String company_code;

	//公司名称
	private String company_name;

	//经理id
	private int manager_id;

	//姓名拼音缩写
	private String pinyinname_short;

	//姓名拼音全拼
	private String pinyinname_full;

	//昵称
	private String nickname;

	//头像地址
	private String picture_url;

	//状态 0 未注册，1 已注册，-1 禁用
	private String status;

	//系统语言
	private String system_language;

}
