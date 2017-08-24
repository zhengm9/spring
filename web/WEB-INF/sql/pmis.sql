drop table if exists `pmis`.`project_info`;
CREATE TABLE IF NOT EXISTS `pmis`.`project_info` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `project_name` VARCHAR(255) NOT NULL,
  `parent_project_id` INT NULL,
  `project_type` TINYINT NOT NULL DEFAULT 0 COMMENT '项目优先级：0-常规，1-紧急，2-特急，3-运维',
  `develop_field` VARCHAR(45) NULL,
  `workload` FLOAT(10,2) NULL COMMENT '工作量大小，单位人天，限100亿以下，小数点后保留2位',
  `handle_state` TINYINT NULL COMMENT '项目当前处理状态',
  `internal_task_state` TINYINT NULL,
  `external_task_state` TINYINT NULL,
  `joint_debug_date` DATE NULL COMMENT '联调时间',
  `requirements_received_date` DATE NULL COMMENT '收到需求时间',
  `owner_id` INT NOT NULL COMMENT '负责人id',
  PRIMARY KEY (`id`),
  INDEX `id_idx` (`owner_id` ASC),
  INDEX `fk_project_info_parent_project_info1_idx` (`parent_project_id` ASC),
  CONSTRAINT `id`
    FOREIGN KEY (`owner_id`)
    REFERENCES `pmis`.`sys_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_info_parent_project_info1`
    FOREIGN KEY (`parent_project_id`)
    REFERENCES `pmis`.`parent_project_info` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

drop table if exists `pmis`.`parent_project_info`;
CREATE TABLE IF NOT EXISTS `pmis`.`parent_project_info` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `parent_project_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;


drop table if exists `pmis`.`sys_user`;
CREATE TABLE `pmis`.`sys_user` (
  `id` int(11) NOT NULL auto_increment,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(50) default NULL,
  `active` tinyint(1) NOT NULL default '1',
  `username` varchar(16) NOT NULL COMMENT '登录名',
  `password` varchar(40) NOT NULL,
  `last_update` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `team_id` tinyint(4) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1
CHARACTER SET utf8 COLLATE utf8_general_ci;

drop table if exists `pmis`.`team_info`;
CREATE TABLE `pmis`.`team_info` (
  `id` tinyint(4) NOT NULL auto_increment,
  `team_name` varchar(40) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1
CHARACTER SET utf8 COLLATE utf8_general_ci;



insert into sys_user (first_name,last_name,username,password,team_id) values ('zheng','ming','zmmason','891230',(select id from team_info));

insert into sys_user (first_name,last_name,username,password,team_id) values ('zheng','ming','zmmason','891230',(select id from team_info));


insert into project_info (project_name,project_type,owner_id) values ('蚂蚁航意险','1',(select id from sys_user));
insert into project_info (project_name,project_type,owner_id,workload,handle_state,requirements_received_date)
  values ('去哪儿退保','2',(select id from sys_user),118.22,2,"2017-07-13");
insert into project_info (project_name,project_type,owner_id,workload,handle_state,requirements_received_date)
  values ('蚂蚁1','2',(select id from sys_user),118.22,2,"2017-07-13");
insert into project_info (project_name,project_type,owner_id,workload,handle_state,requirements_received_date)
  values ('蚂蚁2','2',(select id from sys_user),118.22,2,"2017-07-13");
insert into project_info (project_name,project_type,owner_id,workload,handle_state,requirements_received_date)
  values ('蚂蚁3','2',(select id from sys_user),118.22,2,"2017-07-13");
insert into project_info (project_name,project_type,owner_id,workload,handle_state,requirements_received_date)
  values ('蚂蚁4','2',(select id from sys_user),118.22,2,"2017-07-13");
insert into project_info (project_name,project_type,owner_id,workload,handle_state,requirements_received_date)
  values ('蚂蚁5','2',(select id from sys_user),118.22,2,"2017-07-13");
insert into project_info (project_name,project_type,owner_id,workload,handle_state,requirements_received_date)
  values ('蚂蚁6','2',(select id from sys_user),118.22,2,"2017-07-13");
insert into project_info (project_name,project_type,owner_id,workload,handle_state,requirements_received_date)
  values ('蚂蚁7','2',(select id from sys_user),118.22,2,"2017-07-13");
insert into project_info (project_name,project_type,owner_id,workload,handle_state,requirements_received_date)
  values ('蚂蚁8','2',(select id from sys_user),118.22,2,"2017-07-13");