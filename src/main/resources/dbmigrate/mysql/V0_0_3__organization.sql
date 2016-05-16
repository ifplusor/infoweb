-- 组织机构结构类型
create table organization_struct_type (
    id bigint auto_increment,
    name varchar(50) unique not null,
    descn varchar(200),
    constraint pk_st primary key(id)
) engine=InnoDB default charset=utf8;

-- 组织机构实体类型
create table organization_entity_type (
    id bigint auto_increment,
    struct_type_id bigint not null,
    name varchar(50) not null,
    code int,
    descn varchar(200),
    constraint pk_et primary key(id),
    constraint fk_et_sti foreign key(struct_type_id) references organization_struct_type(id)
) engine=InnoDB default charset=utf8;

alter table organization_struct_type add top_entity_type_id bigint;
alter table organization_struct_type add constraint fk_st_teti foreign key(top_entity_type_id) references organization_struct_type(id);

-- 组织机构结构规则（连接表）
create table organization_struct_rule (
    parent_entity_type_id bigint,
    child_entity_type_id bigint,
    constraint pk_sr primary key(parent_entity_type_id,child_entity_type_id),
    constraint fk_sr_peti foreign key(parent_entity_type_id) references organization_entity_type(id),
    constraint fk_sr_ceti foreign key(child_entity_type_id) references organization_entity_type(id)
) engine=InnoDB default charset=utf8;


-- 组织机构实例
create table organization_instance (
    id bigint auto_increment,
    struct_type_id bigint not null,
    name varchar(50) unique not null,
    descn varchar(200),
    constraint pk_i primary key(id),
    constraint fk_i_ti foreign key(struct_type_id) references organization_struct_type(id)
) engine=InnoDB default charset=utf8;

-- 组织机构实例实体（连接表）
create table organization_instance_entity (
    id bigint auto_increment,
    instance_id bigint not null,
    entity_type_id bigint not null,
    name varchar(50) not null,
    descn varchar(200),
    ref bigint,
    constraint pk_ie primary key(id),
    constraint fk_ie_ii foreign key(instance_id) references organization_instance(id),
    constraint fk_ie_eti foreign key(entity_type_id) references organization_entity_type(id)
) engine=InnoDB default charset=utf8;

-- 组织机构实例结构
create table organization_instance_struct (
    parent_instance_entity_id bigint,
    child_instance_entity_id bigint,
    constraint pk_is primary key(parent_instance_entity_id,child_instance_entity_id),
    constraint fk_is_piei foreign key(parent_instance_entity_id) references organization_instance_entity(id),
    constraint fk_is_ciei foreign key(child_instance_entity_id) references organization_instance_entity(id)
) engine=InnoDB default charset=utf8;


insert into organization_struct_type(id,name,descn) values(1,'高校','普通高校');

insert into organization_entity_type(id,struct_type_id,name,code,descn) values(1,1,'学校',0,'');
insert into organization_entity_type(id,struct_type_id,name,code,descn) values(2,1,'二级学院',0,'');

update organization_struct_type set top_entity_type_id = 1 where id = 1;

insert into organization_struct_rule(parent_entity_type_id,child_entity_type_id) values(1,2);

insert into organization_instance(id,struct_type_id,name,descn) values(1,1,'沈阳航空航天大学','');

insert into organization_instance_entity(id,instance_id,entity_type_id,name,descn,ref) values(1,1,1,'沈阳航空航天大学','',null);
insert into organization_instance_entity(id,instance_id,entity_type_id,name,descn,ref) values(2,1,2,'计算机学院','',null);

insert into organization_instance_struct(parent_instance_entity_id,child_instance_entity_id) values(1,2);
