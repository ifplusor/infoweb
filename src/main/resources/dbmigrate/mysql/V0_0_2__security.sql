-- 资源
create table security_resc(
    id bigint auto_increment,
    name varchar(50) not null,
    res_type varchar(50),
    res_string varchar(200),
    priority integer,
    descn varchar(200),
    constraint pk_resc primary key(id)
);

-- 角色
create table security_role(
    id bigint auto_increment,
    name varchar(50) not null,
    descn varchar(200),
    constraint pk_role primary key(id)
);

-- 用户
create table security_user(
    id bigint auto_increment,
    username varchar(50) not null,
    password varchar(50) not null,
    status integer not null,
    descn varchar(200),
    constraint pk_user primary key(id)
);

-- 资源角色连接表
create table security_resc_role(
    resc_id bigint not null,
    role_id bigint not null,
    constraint pk_resc_role primary key(resc_id, role_id),
    constraint fk_resc_role_resc foreign key(resc_id) references security_resc(id),
    constraint fk_resc_role_role foreign key(role_id) references security_role(id)
);

-- 用户角色连接表
create table security_user_role(
    user_id bigint not null,
    role_id bigint not null,
    constraint pk_user_role primary key(user_id, role_id),
    constraint fk_user_role_user foreign key(user_id) references security_user(id),
    constraint fk_user_role_role foreign key(role_id) references security_role(id)
);


insert into security_user(id,username,password,status,descn) values(1,'admin','admin',1,'管理员');
insert into security_user(id,username,password,status,descn) values(2,'user','user',1,'用户');

insert into security_role(id,name,descn) values(1,'IS_AUTHENTICATED_ANONYMOUSLY','游客');
insert into security_role(id,name,descn) values(2,'ROLE_SUPER','超级管理员');
insert into security_role(id,name,descn) values(3,'ROLE_ADMIN','管理员');
insert into security_role(id,name,descn) values(4,'ROLE_USER','用户');

insert into security_resc(id,name,res_type,res_string,priority,descn) values(1,'','URL','/login.jsp',0,'登陆');
insert into security_resc(id,name,res_type,res_string,priority,descn) values(2,'','URL','/mvc/print/**',1,'');
insert into security_resc(id,name,res_type,res_string,priority,descn) values(3,'','URL','/**',2,'');

insert into security_user_role(user_id,role_id) values(1,3);
insert into security_user_role(user_id,role_id) values(1,4);
insert into security_user_role(user_id,role_id) values(2,4);

insert into security_resc_role(resc_id,role_id) values(1,1);
insert into security_resc_role(resc_id,role_id) values(2,3);
insert into security_resc_role(resc_id,role_id) values(3,3);
insert into security_resc_role(resc_id,role_id) values(3,4);

