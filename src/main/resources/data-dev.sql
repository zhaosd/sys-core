/* 初始化系统用户数据 */
insert into sys_user (id, username, password) values (1, 'root', '6E3CDB6983ABE0E2247D192F0A6965F0F71DFB0BA799BEA57010947A');
insert into sys_user (id, username, password) values (2, 'test', 'DD6D998B2133F4197212D83BA9C818481C169930482122088661DD1D');
insert into sys_user (id, username, password) values (3, 'user1', 'userpass');
insert into sys_user (id, username, password) values (4, 'user2', 'userpass2');

insert into sys_role (id, name) values (1, '系统管理员');

insert into sys_user_role(uid, rid) values (1, 1);

insert into sys_module (code, name, description) values ('system', '系统功能', '系统管理功能模块');

/* 初始化权限定义数据 */
insert into sys_permission (permission, name, description, mid) values ('sys.user.admin', '用户管理', '对系统用户增删改查', (select id from sys_module where code = 'system'));
insert into sys_permission (permission, name, description, mid) values ('sys.role.admin', '角色管理', '对系统角色增删改查', (select id from sys_module where code = 'system'));
insert into sys_permission (permission, name, description, mid) values ('sys.permission.admin', '权限管理', '对系统权限增删改查', (select id from sys_module where code = 'system'));
insert into sys_permission (permission, name, description, mid) values ('sys.log.view', '日志查看', '查看系统访问日志', (select id from sys_module where code = 'system'));

insert into sys_user_permission(uid, pid) values (1, (select id from sys_permission where permission = 'sys.user.admin'));

# test data
insert into sys_module (code, name, description) values ('sample', '样例界面', '各种类型页面样例');

insert into sys_permission (permission, name, description, mid) values ('sample', '样例功能', '样例功能', (select id from sys_module where code = 'sample'));
insert into sys_permission (permission, name, description, mid) values ('admin', '管理权限样例', '管理权限样例', (select id from sys_module where code = 'sample'));
insert into sys_permission (permission, name, description, mid) values ('editor', '编辑权限样例', '编辑权限样例', (select id from sys_module where code = 'sample'));

insert into sys_role_permission(rid, pid) values (1, (select id from sys_permission where permission = 'sys.user.admin'));
insert into sys_role_permission(rid, pid) values (1, (select id from sys_permission where permission = 'sys.role.admin'));
insert into sys_role_permission(rid, pid) values (1, (select id from sys_permission where permission = 'sys.permission.admin'));
insert into sys_role_permission(rid, pid) values (1, (select id from sys_permission where permission = 'sys.log.view'));
insert into sys_role_permission(rid, pid) values (1, (select id from sys_permission where permission = 'sample'));
insert into sys_role_permission(rid, pid) values (1, (select id from sys_permission where permission = 'admin'));
insert into sys_role_permission(rid, pid) values (1, (select id from sys_permission where permission = 'editor'));

insert into sys_role (id, name) values (2, '测试角色');
insert into sys_role_permission(rid, pid) values (2, (select id from sys_permission where permission = 'sys.user.admin'));