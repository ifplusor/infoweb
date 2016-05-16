create table cms_folder (
    id bigint auto_increment,
    owner_id bigint not null,
    parent_folder_id bigint,
    name varchar(50) not null,
    auth char(6) not null,
    descn varchar(200),
    constraint pk_folder primary key(id),
    constraint fk_folder_user foreign key(owner_id) references security_user(id),
    constraint fk_folder_folder foreign key(parent_folder_id) references cms_folder(id)
);

create table cms_file (
    id bigint auto_increment,
    owner_id bigint not null,
    parent_folder_id bigint not null,
    name varchar(50) not null,
    auth char(6) not null,
    uri varchar(200),
    descn varchar(200),
    constraint pk_file primary key(id),
    constraint fk_file_user foreign key(owner_id) references security_user(id),
    constraint fk_file_folder foreign key(parent_folder_id) references cms_folder(id)
);

insert cms_folder(id,owner_id,parent_folder_id,name,auth,descn) values(1,1,null,'/','rw----','top folder');
update cms_folder set parent_folder_id = 1 where id = 1;
alter table cms_folder modify parent_folder_id bigint not null;