create table users (
 username varchar(50) primary key,
 password varchar(50),
 enabled tinyint(1)
);

create table authorities (
 id int auto_increment primary key,
 username varchar(50),
 authority varchar(50),
 constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

