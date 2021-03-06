DROP TABLE IF EXISTS t_employee;
CREATE TABLE t_employee(
        ID INT  PRIMARY KEY  AUTO_INCREMENT,
        NAME VARCHAR(30) NOT NULL,
        SEX  VARCHAR(2) NOT NULL,
        AGE INT NOT NULL,
        DEPARTMENT VARCHAR(10) NOT NULL,
        SALARY  INT NOT NULL,
        HOME VARCHAR(30),
        MARRY VARCHAR(10) NOT NULL DEFAULT '否',       
        HOBBY VARCHAR(30)
 )ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS t_employee_detail;
create TABLE t_employee_detail(
ID INT PRIMARY KEY,
POS CHAR(10) NOT NULL,
EXPERENCE CHAR(10) NOT NULL,
CONSTRAINT `FK_ID` FOREIGN KEY(ID) REFERENCES t_employee(ID)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE VIEW V_VIEW1(ID, NAME, SEX, AGE,DEPARTMENT) AS SELECT ID, NAME, SEX, AGE,DEPARTMENT FROM t_employee;

CREATE VIEW V_VIEW2(ID, NAME, SEX, AGE,DEPARTMENT,POS,EXPERENCE)
 AS SELECT a.ID, a.NAME, a.SEX, a.AGE,a.DEPARTMENT,b.POS,b.EXPERENCE FROM t_employee a,t_employee_detail b WHERE a.ID=b.ID;

INSERT INTO t_employee(ID, NAME, SEX, AGE,DEPARTMENT, SALARY, HOME, MARRY, HOBBY) VALUES(NULL,'小红','女',20,'人事部','4000','广东','否','网球');
INSERT INTO t_employee(ID, NAME, SEX, AGE,DEPARTMENT, SALARY, HOME, MARRY, HOBBY) VALUES(NULL,'明日','女',21,'人事部','9000','北京','否','网球');
INSERT INTO t_employee(ID, NAME, SEX, AGE,DEPARTMENT, SALARY, HOME, MARRY, HOBBY) VALUES(NULL,'天天','男',22,'研发部','8000','上海','否','音乐');
INSERT INTO t_employee(ID, NAME, SEX, AGE,DEPARTMENT, SALARY, HOME, MARRY, HOBBY) VALUES(NULL,'大大','女',23,'研发部','9000','重庆','否','无');
INSERT INTO t_employee(ID, NAME, SEX, AGE,DEPARTMENT, SALARY, HOME, MARRY, HOBBY) VALUES(NULL,'王下','女',24,'研发部','9000','四川','是','足球');
INSERT INTO t_employee(ID, NAME, SEX, AGE,DEPARTMENT, SALARY, HOME, MARRY, HOBBY) VALUES(NULL,'无名','男',25,'销售部','6000','福建','否','游戏');
INSERT INTO t_employee(ID, NAME, SEX, AGE,DEPARTMENT, SALARY, HOME, MARRY, HOBBY) VALUES(NULL,'不知道','女',26,'销售部','5000','山西','否','篮球');

INSERT INTO t_employee_detail(ID,POS,EXPERENCE) VALUES(1,'人事管理','工作二年');
INSERT INTO t_employee_detail(ID,POS,EXPERENCE) VALUES(2,'人事招聘','工作二年');
INSERT INTO t_employee_detail(ID,POS,EXPERENCE) VALUES(3,'初级工程师','工作一年');
INSERT INTO t_employee_detail(ID,POS,EXPERENCE) VALUES(4,'中级工程师','工作二年');
INSERT INTO t_employee_detail(ID,POS,EXPERENCE) VALUES(5,'高级工程师','工作三年');
INSERT INTO t_employee_detail(ID,POS,EXPERENCE) VALUES(6,'销售代表','工作二年');
INSERT INTO t_employee_detail(ID,POS,EXPERENCE) VALUES(7,'销售员','工作一年');