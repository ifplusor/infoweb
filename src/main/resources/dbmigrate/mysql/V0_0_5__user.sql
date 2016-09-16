-- 修改用户表结构，增加详细信息
alter table security_user add email varchar(325);
alter table security_user add phone varchar(15);

-- 因配置摘要认证，修改帐号密码
update security_user set password='infoweb' where username='ifplusor';
update security_user set password='admin' where username='admin';
update security_user set password='user' where username='user';
