create table PA_VIVER_LOST(
       id    varchar2(100) primary key,
       image varchar2(80),
     title varchar2(200),
     content varchar2(4000),
     name varchar2(20),
     user_id varchar2(100),
     create_date varchar2(20),
     update_date varchar2(20),
     province    varchar2(15),
     city        varchar2(15),
     is_lost     varchar(2),
     money       number,
     remark      varchar(4000)
);
comment on table PA_VIVER_LOST is '失物招领表';
comment on column PA_VIVER_LOST.id is '主键';
comment on column PA_VIVER_LOST.image is '失物图片地址';
comment on column PA_VIVER_LOST.title is '失物主题';
comment on column PA_VIVER_LOST.content is '失物内容';
comment on column PA_VIVER_LOST.name is '失主名字';
comment on column PA_VIVER_LOST.user_id is '失主id';
comment on column PA_VIVER_LOST.create_date is '发布日期';
comment on column PA_VIVER_LOST.update_date is '更新日期';
comment on column PA_VIVER_LOST.province is '所属省份';
comment on column PA_VIVER_LOST.city is '所属城市';
comment on column PA_VIVER_LOST.is_lost is '遗失状态：Y-遗失  N-找回';
comment on column PA_VIVER_LOST.money is '酬金';
comment on column PA_VIVER_LOST.remark is '备注';