-- Create table
create table PA_VIVER_LOST_REG
(
  id          VARCHAR2(100) primary key,
  account     VARCHAR2(80),
  password    VARCHAR2(80),
  email       VARCHAR2(30),
  qq          VARCHAR2(12),
  phone       VARCHAR2(12),
  age         INTEGER,
  sex         INTEGER,
  create_date VARCHAR2(20),
  is_delete   VARCHAR2(2)
);
-- Add comments to the table
comment on table PA_VIVER_LOST_REG
  is '失物招领用户注册表';
-- Add comments to the columns
comment on column PA_VIVER_LOST_REG.id
  is '主键';
comment on column PA_VIVER_LOST_REG.account
  is '账号';
comment on column PA_VIVER_LOST_REG.password
  is '密码';
comment on column PA_VIVER_LOST_REG.email
  is '邮箱';
comment on column PA_VIVER_LOST_REG.qq
  is 'QQ';
comment on column PA_VIVER_LOST_REG.phone
  is '手机号码';
comment on column PA_VIVER_LOST_REG.age
  is '年龄';
comment on column PA_VIVER_LOST_REG.sex
  is '性别';
comment on column PA_VIVER_LOST_REG.create_date
  is '注册日期';
comment on column PA_VIVER_LOST_REG.is_delete
  is '是否失效 否-N 是-Y';
