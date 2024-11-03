-- drop tables if they exist
drop table if exists cubic_information cascade;
drop table if exists cubic_rely_information cascade;
drop table if exists cubic_thread_dump cascade;
drop table if exists cubic_thread_pool cascade;
drop table if exists cubic_user cascade;

-- ----------------------------
-- table structure for cubic_information
-- ----------------------------
create table cubic_information
(
    id             serial primary key,
    app_id         varchar(100) not null,
    instance_name  varchar(150),
    instance_id    varchar(100),
    ip             varchar(30),
    progress       varchar(10),
    host           varchar(30),
    os             varchar(10),
    os_arch        varchar(20),
    os_version     varchar(128),
    language       varchar(10),
    version        varchar(5),
    last_heartbeat timestamp,
    start_date     timestamp,
    jdk_version    varchar(20),
    jdk_dir        varchar(200),
    user_dir       varchar(200),
    init_memory    int,
    max_memory     int,
    processor_num  int,
    arguments      text,
    jars           text,
    unique (app_id)
);

-- table comment
comment on table cubic_information is '应用实例信息表';

-- column comments
comment on column cubic_information.id is '序号';
comment on column cubic_information.app_id is '应用标识';
comment on column cubic_information.instance_name is '实例名称';
comment on column cubic_information.instance_id is '实例id';
comment on column cubic_information.ip is 'ip 地址';
comment on column cubic_information.progress is '进程编号';
comment on column cubic_information.host is '主机名';
comment on column cubic_information.os is '操作系统';
comment on column cubic_information.os_arch is '操作系统架构';
comment on column cubic_information.os_version is '操作系统版本';
comment on column cubic_information.language is '编程语言';
comment on column cubic_information.version is '版本号';
comment on column cubic_information.last_heartbeat is '最后心跳时间';
comment on column cubic_information.start_date is '启动时间';
comment on column cubic_information.jdk_version is 'jdk版本';
comment on column cubic_information.jdk_dir is 'jdk路径';
comment on column cubic_information.user_dir is '用户路径';
comment on column cubic_information.init_memory is '初始内存';
comment on column cubic_information.max_memory is '最大内存';
comment on column cubic_information.processor_num is '处理器数量';
comment on column cubic_information.arguments is '启动参数';
comment on column cubic_information.jars is '依赖jar包';

-- ----------------------------
-- table structure for cubic_rely_information
-- ----------------------------
create table cubic_rely_information
(
    id           serial primary key,
    app_id       varchar(64),
    jar_name     varchar(100),
    service_name varchar(50),
    create_time  timestamp
);

-- table comment
comment on table cubic_rely_information is '实例依赖信息表';

-- column comments
comment on column cubic_rely_information.id is '序号';
comment on column cubic_rely_information.app_id is '实例唯一id';
comment on column cubic_rely_information.jar_name is 'jar包名称';
comment on column cubic_rely_information.service_name is '应用名称';
comment on column cubic_rely_information.create_time is '创建时间';

-- ----------------------------
-- table structure for cubic_thread_dump
-- ----------------------------
create table cubic_thread_dump
(
    id            bigserial primary key,
    app_id        varchar(100) not null,
    instance_name varchar(150),
    instance_id   varchar(100),
    thread_dump   text,
    create_time   timestamp
);

-- table comment
comment on table cubic_thread_dump is '线程栈数据表';

-- column comments
comment on column cubic_thread_dump.id is '唯一主键';
comment on column cubic_thread_dump.app_id is '应用标识';
comment on column cubic_thread_dump.instance_name is '实例名称';
comment on column cubic_thread_dump.instance_id is '实例id';
comment on column cubic_thread_dump.thread_dump is '线程栈信息';
comment on column cubic_thread_dump.create_time is '采集时间';

-- ----------------------------
-- table structure for cubic_thread_pool
-- ----------------------------
create table cubic_thread_pool
(
    id                 bigserial primary key,
    instance_id        varchar(100),
    instance_name      varchar(150),
    thread_pool_key    varchar(100) not null,
    thread_pool_params text,
    create_time        timestamp
);

-- table comment
comment on table cubic_thread_pool is '线程池数据表';

-- column comments
comment on column cubic_thread_pool.id is '唯一主键';
comment on column cubic_thread_pool.instance_id is '应用名称';
comment on column cubic_thread_pool.instance_name is '实例名称';
comment on column cubic_thread_pool.thread_pool_key is '线程池key';
comment on column cubic_thread_pool.thread_pool_params is '线程池参数';
comment on column cubic_thread_pool.create_time is '采集时间';

-- ----------------------------
-- table structure for cubic_user
-- ----------------------------
create table cubic_user
(
    id          serial primary key,
    username    varchar(50)  not null,
    secret      varchar(255) not null,
    status      smallint default 1,
    is_admin    smallint default 0,
    create_time timestamp
);

-- table comment
comment on table cubic_user is '用户表';

-- column comments
comment on column cubic_user.id is '序号';
comment on column cubic_user.username is '用户名';
comment on column cubic_user.secret is '秘钥';
comment on column cubic_user.status is '状态 1 正常, 0 禁用';
comment on column cubic_user.is_admin is '是否为管理员 0 不是, 1 是';
comment on column cubic_user.create_time is '创建时间';

-- insert data into the cubic_user table
insert into cubic_user (id, username, secret, create_time)
values (1, 'matrix', '123456', '2020-11-25 12:08:51');