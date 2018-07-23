CREATE TABLE `sys_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '登陆名',
  `first_name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `last_name` varchar(64) DEFAULT NULL COMMENT '用户姓',
  `password` varchar(255) DEFAULT NULL COMMENT '加密密码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `email` varchar(128) DEFAULT NULL COMMENT 'Email',
  `mobile` varchar(64) DEFAULT NULL COMMENT '手机号',
  `description` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sys_username` (`username`) COMMENT '用户登录名唯一约束'
) ENGINE=InnoDB COMMENT '系统用户表';

CREATE TABLE `sys_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '角色名',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_role_name` (`name`) COMMENT '角色名唯一约束'
) ENGINE=InnoDB COMMENT '系统角色表';

CREATE TABLE `sys_user_role` (
  `uid` bigint(11) NOT NULL COMMENT '用户ID',
  `rid` bigint(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`uid`, `rid`)
) ENGINE=InnoDB COMMENT '用户角色表';

CREATE TABLE `sys_module` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL COMMENT '模块编码',
  `name` varchar(255) DEFAULT NULL COMMENT '模块名称',
  `description` varchar(255) DEFAULT NULL COMMENT '模块描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_module` (`code`)
) ENGINE=InnoDB COMMENT '系统模块表';

CREATE TABLE `sys_permission` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `permission` varchar(64) NOT NULL COMMENT '权限编码',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `mid` bigint(11) NOT NULL COMMENT '模块ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_permission` (`permission`)
) ENGINE=InnoDB COMMENT '系统选线表';

CREATE TABLE `sys_user_permission` (
  `uid` bigint(11) NOT NULL COMMENT '用户id',
  `pid` bigint(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`uid`, `pid`)
) ENGINE=InnoDB COMMENT '用户权限表';

CREATE TABLE `sys_role_permission` (
  `rid` bigint(11) NOT NULL COMMENT '角色ID',
  `pid` bigint(11) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`rid`, `pid`)
) ENGINE=InnoDB COMMENT '角色权限表';

CREATE TABLE `sys_log` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `action` varchar(128) NOT NULL COMMENT '操作',
  `parameter` varchar(255) DEFAULT NULL COMMENT '参数',
  `uid` bigint(11) DEFAULT NULL COMMENT '操作用户id',
  `ip` varchar(64) DEFAULT NULL COMMENT '客户端IP',
  `host` varchar(64) DEFAULT NULL COMMENT '客户端Host',
  `agent` varchar(255) DEFAULT NULL COMMENT '浏览器',
  `language` varchar(64) DEFAULT NULL COMMENT '语言',
  `referer` varchar(255) DEFAULT NULL COMMENT '跳入地址',
  `origin` varchar(255) DEFAULT NULL COMMENT '来源地址',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '日志记录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT '系统日志表';