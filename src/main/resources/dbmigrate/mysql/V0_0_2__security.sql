-- 资源
create table security_resc (
    id bigint,
    name varchar(50) not null,
    resc_type varchar(50),
    resc_string varchar(200),
    priority integer,
    descn varchar(200),
    constraint pk_resc primary key(id)
) engine=InnoDB default charset=utf8;

-- 角色
create table security_role (
    id bigint,
    name varchar(50) not null,
    descn varchar(200),
    constraint pk_role primary key(id)
) engine=InnoDB default charset=utf8;

-- 用户
create table security_user (
    id bigint,
    username varchar(50) unique not null,
    password varchar(50) not null,
    status integer not null,
    descn varchar(200),
    constraint pk_user primary key(id)
) engine=InnoDB default charset=utf8;

-- 资源角色连接表
create table security_role_resc (
    role_id bigint,
    resc_id bigint,
    constraint pk_role_resc primary key(role_id, resc_id),
    constraint fk_role_resc_role foreign key(role_id) references security_role(id),
    constraint fk_role_resc_resc foreign key(resc_id) references security_resc(id)
) engine=InnoDB default charset=utf8;

-- 用户角色连接表
create table security_user_role (
    user_id bigint,
    role_id bigint,
    constraint pk_user_role primary key(user_id, role_id),
    constraint fk_user_role_user foreign key(user_id) references security_user(id),
    constraint fk_user_role_role foreign key(role_id) references security_role(id)
) engine=InnoDB default charset=utf8;

-- 组
create table security_group (
    id bigint,
    groupname varchar(50) unique not null,
    descn varchar(200),
    constraint pk_group primary key(id)
) engine=InnoDB default charset=utf8;

-- 组用户连接表
create table security_group_user (
    group_id bigint,
    user_id bigint,
    constraint pk_group_user primary key(group_id,user_id),
    constraint fk_group_user_group foreign key(group_id) references security_group(id),
    constraint fk_group_user_user foreign key(user_id) references security_user(id)
) engine=InnoDB default charset=utf8;

-- 组角色连接表
create table security_group_role (
    group_id bigint,
    role_id bigint,
    constraint pk_group_role primary key(group_id,role_id),
    constraint fk_group_role_group foreign key(group_id) references security_group(id),
    constraint fk_group_role_role foreign key(role_id) references security_role(id)
) engine=InnoDB default charset=utf8;

insert into security_user(id,username,password,status,descn) values(1,'ifplusor',md5('infoweb{infoweb}'),1,'super');
insert into security_user(id,username,password,status,descn) values(2,'admin',md5('admin{infoweb}'),1,'管理员');
insert into security_user(id,username,password,status,descn) values(3,'user',md5('user{infoweb}'),1,'用户');

insert into security_role(id,name,descn) values(1,'IS_AUTHENTICATED_ANONYMOUSLY','游客');
insert into security_role(id,name,descn) values(2,'ROLE_SUPER','超级管理员');
insert into security_role(id,name,descn) values(3,'ROLE_ADMIN','管理员');
insert into security_role(id,name,descn) values(4,'ROLE_USER','用户');

insert into security_resc(id,name,resc_type,resc_string,priority,descn) values(1,'','URL','/guest/**',0,'游客页面');
insert into security_resc(id,name,resc_type,resc_string,priority,descn) values(2,'','URL','/super/**',1,'超级管理员页面');
insert into security_resc(id,name,resc_type,resc_string,priority,descn) values(3,'','URL','/admin/**',2,'管理员页面');
insert into security_resc(id,name,resc_type,resc_string,priority,descn) values(4,'','URL','/**',999,'用户页面');

-- 超级管理员需要单独授权
insert into security_user_role(user_id,role_id) values(1,2);

insert into security_role_resc(role_id,resc_id) values(1,1);
insert into security_role_resc(role_id,resc_id) values(2,2);
insert into security_role_resc(role_id,resc_id) values(3,3);
insert into security_role_resc(role_id,resc_id) values(3,4);
insert into security_role_resc(role_id,resc_id) values(4,4);

insert into security_group(id,groupname,descn) values(1,'admin','管理员组');
insert into security_group(id,groupname,descn) values(2,'user','用户组');

-- 管理员，用户 通过组授权
insert into security_group_role(group_id,role_id) values(1,3);
insert into security_group_role(group_id,role_id) values(2,4);

insert into security_group_user(group_id,user_id) values(1,2);
insert into security_group_user(group_id,user_id) values(2,1);
insert into security_group_user(group_id,user_id) values(2,3);

