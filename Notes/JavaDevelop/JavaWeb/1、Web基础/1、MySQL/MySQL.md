# MySQL

![MySQL1](C:\Users\25798\OneDrive\Notes\image\MySQL1.png)

**关系型数据库管理系统：**

Oracle、MySQL、SQL Server

所有关系型数据库都使用SQL语言进行操作

默认端口号为：3306

Windows启动MySQL服务命令：**net start mysql80**

关闭：**net stop mysql80**

客户端连接命令：

mysql [-h 127.0.0.1] [-p 3306] -u root -p

MySQL是关系型数据库，是基于二维表进行数据存储的。

我们可以通过MySQL客户端连接数据库管理系统DBMS，然后通过DBMS操作数据库。 可以使用SQL语句，通过数据库管理系统操作数据库，以及操作数据库中的表结构及数据。 一个数据库服务器中可以创建多个数据库，一个数据库中也可以包含多张表，而一张表中又可以包 含多行记录。

# SQL

全称 Structured  Query Language，结构化查询语言。操作关系型数据库的编程语言，定义了 一套操作关系型数据库统一标准 。

1、SQL语句可以单行或多行书写，以分号结尾。

2、SQL语句可以使用空格/缩进来增强语句的可读性。 

3、MySQL数据库的SQL语句**不区分大小写**，**关键字建议使用大写**。 

4、注释： 

单行注释：-- 注释内容  或  # 注释内容(MySQL特有) 

多行注释：/* 注释内容 */

![MySQL2](C:\Users\25798\OneDrive\Notes\image\MySQL2.png)

## DDL

Data Definition Language，数据定义语言，用来定义数据库对象(数据库，表，字段) 。

**数据库操作：**

```mysql
-- 查询所有数据库
show databases;
-- 查询当前数据库
select database();
-- 创建数据库
create database/schema [ if not exists ] 数据库名 [ default charset 字符集 ] [ collate 排序规则 ];
-- 删除数据库
drop database [ if exists ] 数据库名;
-- 切换使用数据库
use 数据库名;
```

**表操作：**

```mysql
-- 查询当前数据库所有表
show tables;

-- 查看指定表结构
desc 表名;

-- 查询指定表的建表语句
show create table 表名;
/* 通过这条指令，主要是用来查看建表语句的，而有部分参数我们在创建表的时候，并未指定也会查询到，因为这部分是数据库的默认值，如：存储引擎、字符集等 */

-- 创建表结构
CREATE TABLE  表名(
	字段1  字段1类型 [COMMENT '字段1注释'],
	字段2  字段2类型 [COMMENT '字段2注释'],
	字段3  字段3类型 [COMMENT '字段3注释'],
 	......
	字段n  字段n类型 [COMMENT '字段n注释']
) [COMMENT '表注释'];
-- 注意: [...] 内为可选参数，最后一个字段后面没有逗号
```

**MySQL中的数据类型**有很多，主要分为三类：**数值类型、字符串类型、日期时间类型。**

| 数值类型    | 大小   | 有符号(SIGNED)范围                                      | 无符号(UNSIGNED)范围                                    | 描述               |
| ----------- | ------ | ------------------------------------------------------- | ------------------------------------------------------- | ------------------ |
| TINYINT     | 1byte  | (-128，127)                                             | (0，255)                                                | 小整数值           |
| SMALLINT    | 2bytes | (-32768，32767)                                         | (0，65535)                                              | 大整数值           |
| MEDIUMINT   | 3bytes | (-8388608，8388607)                                     | (0，16777215)                                           | 大整数值           |
| INT/INTEGER | 4bytes | (-2147483648， 2147483647)                              | (0，4294967295)                                         | 大整数值           |
| BIGINT      | 8bytes | (-2^63，2^63-1)                                         | (0，2^64-1)                                             | 极大整数值         |
| FLOAT       | 4bytes | (-3.402823466 E+38， 3.402823466351 E+38)               | 0和(1.175494351 E 38，3.402823466 E+38)                 | 单精度浮点数 值    |
| DOUBLE      | 8bytes | (-1.7976931348623157 E+308，  1.7976931348623157 E+308) | 0和(2.2250738585072014 E-308，1.7976931348623157 E+308) | 双精度浮点数 值    |
| DECIMAL     |        | 依赖于M(精度)和D(标度)的值                              | 依赖于M(精度)和D(标度)的值                              | 小数值(精确定点数) |

字符串类型：char 与 varchar 都可以描述字符串，char是定长字符串，指定长度多长，就占用多少个字符，和字段值的长度无关 。而varchar是变长字符串，指定的长度为最大占用长度 。相对来说，char的性能会更高些。

![MySQL3](C:\Users\25798\OneDrive\Notes\image\MySQL3.png)

日期类型：

![MySQL4](C:\Users\25798\OneDrive\Notes\image\MySQL4.png)

```mysql
create table emp(
 id int comment '编号',
 workno varchar(10) comment '工号',
 name varchar(10) comment '姓名',
 gender char(1) comment '性别',
 age tinyint unsigned comment '年龄',
 idcard char(18) comment '身份证号',
 entrydate date comment '入职时间'
 ) comment '员工表';
```

**表操作 - 修改：**

```mysql
-- 添加表中字段
ALTER TABLE 表名 ADD 字段名 类型 (长度)  [COMMENT 注释] [约束];

-- 修改数据类型
ALTER TABLE 表名 MODIFY 字段名 新数据类型(长度);

-- 修改字段名和字段类型
ALTER TABLE 表名 CHANGE 旧字段名 新字段名 类型(长度) [COMMENT 注释] [约束];

-- 删除字段
ALTER TABLE 表名 DROP 字段名;

-- 修改表名
ALTER TABLE 表名 RENAME TO 新表名;
```

**表操作-删除：**

```mysql
 -- 删除表
DROP TABLE [IF EXISTS] 表名;

-- 删除指定表, 并重新创建表
TRUNCATE TABLE 表名;
-- 注意: 在删除表的时候，表中的全部数据也都会被删除。
```

## DML

DML英文全称是Data Manipulation Language(数据操作语言)，用来对数据库中表的数据记录进 行增、删、改操作。

添加数据（INSERT） 修改数据（UPDATE） 删除数据（DELETE）

**添加数据：**

```mysql
-- 给指定字段添加数据
INSERT INTO 表名 (字段名1, 字段名2, ...) VALUES (值1, 值2, ...);

-- 查询数据库数据的SQL语句: 
select * from employee;

-- 给全部字段添加数据
INSERT INTO 表名 VALUES (值1, 值2, ...);

/* 注意事项: 
• 插入数据时，指定的字段顺序需要与值的顺序是一一对应的。
• 字符串和日期型数据应该包含在引号中。
• 插入的数据大小，应该在字段的规定范围内。 */
```

 **修改数据：**

```mysql
UPDATE 表名 SET 字段名1 = 值1, 字段名2 = 值2, ....[WHERE 条件];
/* 注意事项:
修改语句的条件可以有，也可以没有，如果没有条件，则会修改整张表的所有数据。*/
```

 **删除数据：**

```mysql
DELETE FROM 表名 [WHERE 条件];
/* 注意事项:
• DELETE 语句的条件可以有，也可以没有，如果没有条件，则会删除整张表的所有数
据。
• DELETE 语句不能删除某一个字段的值(可以使用UPDATE，将该字段值置为NULL即
可)。
• 当进行删除全部数据操作时，datagrip会提示我们，询问是否确认删除，我们直接点击
Execute即可。 */
```

## DQL

DQL英文全称是Data Query Language(数据查询语言)，数据查询语言，用来查询数据库中表的记录。

查询关键字: SELECT

**DQL 查询语句**，**语法结构**如下：

```mysql
SELECT
    -- 字段列表
FROM
    -- 表名列表
WHERE
    -- 条件列表
GROUP BY
    -- 分组字段列表
HAVING
    -- 分组后条件列表
ORDER BY
    -- 排序字段列表
LIMIT
    -- 分页参数
```

**DQL语句的执行顺序**为： 

from -> where -> group by ->  having -> select -> order by -> limit 

### 基本查询

在基本查询的DQL语句中，不带任何的查询条件

```mysql
-- 查询多个字段
 SELECT 字段1, 字段2, 字段3 ... FROM 表名;
 SELECT * FROM 表名;
-- 注意 : * 号代表查询所有字段，在实际开发中尽量少用（不直观、影响效率）。

-- 字段设置别名
SELECT 字段1 AS 别名1, 字段2 AS 别名2 ... FROM  表名;
SELECT 字段1 别名1, 字段2 别名2 ... FROM 表名;-- AS可以省略

-- 去除重复记录
SELECT DISTINCT 字段列表 FROM 表名;
```

#### LIMIT

1.在使用LIMIT子句进行分页查询时，通常将LIMIT子句放在查询的最后。
2.不同的数据库管理系统对LIMIT子句的支持可能会有所不同，因此在跨数据库平台时需要注意SQL语句的兼容性。
3.当查询结果集为空时，使用LIMIT关键字可能会导致错误。

```mysql
-- LIMIT限制返回结果
SELECT * FROM table_name LIMIT 5; -- 将返回table_name表中的前5条记录。
SELECT * FROM table_name LIMIT 2, 5; -- table_name表中从第3条记录开始的5条记录。
```



### 条件查询

```mysql
SELECT 字段列表 FROM 表名 WHERE 条件列表;
```

![MySQL5](C:\Users\25798\OneDrive\Notes\image\MySQL5.png)

![MySQL6](C:\Users\25798\OneDrive\Notes\image\MySQL6.png)

### 聚合函数

将一列数据作为一个整体，进行**纵向计算**。

```mysql
SELECT 聚合函数(字段列表) FROM 表名;
-- NULL值是不参与所有聚合函数运算的。
```

count() 统计数量

max() 最大值

min() 最小值

avg() 平均值

sum() 求和

对于**count聚合函数**，统计符合条件的总记录数，还可以通过 count(数字/字符串)的形式进行统计 查询

### 分组查询

```mysql
SELECT 字段列表 FROM 表名 [WHERE 条件] GROUP BY 分组字段名 [HAVING 分组后过滤条件];
/* 注意事项:
 • 分组之后，查询的字段一般为聚合函数和分组字段，查询其他字段无任何意义。
• 执行顺序: where > 聚合函数 > having 。
• 支持多字段分组, 具体语法为 : group by columnA,columnB
*/
-- 根据性别分组 , 统计男性员工 和 女性员工的数量
select gender, count(*) from emp group by gender ;
```

**where与having区别** 

执行时机不同：where是分组之前进行过滤，不满足where条件，不参与分组；而having是分组 之后对结果进行过滤。 

判断条件不同：where不能对聚合函数进行判断，而having可以。

**`GROUP BY` 中列出的字段以外的字段必须应用聚合函数（例如 `MIN()`、`MAX()` 等）。**

### 排序查询

排序在日常开发中是非常常见的一个操作，有升序排序，也有降序排序

```mysql
SELECT 字段列表 FROM 表名 ORDER BY 字段1 排序方式1, 字段2 排序方式2;
```

**排序方式** 

ASC : 升序(默认值)  

DESC: 降序

**注意事项**： 

• 如果是升序, 可以不指定排序方式ASC ; 

• 如果是多字段排序，当第一个字段值相同时，才会根据第二个字段进行排序 

### 窗口查询

需要**在每组内排名**，因为窗口函数是对where或者group by子句处理后的结果进行操作，所以**窗口函数原则上只能写在select子句中**。

```mysql
函数 over (partition by 用于分组的列名
                order by 用于排序的列名)
```

1） 专用窗口函数，rank, dense_rank, row_number等专用窗口函数。

2） 聚合函数，如sum. avg, count, max, min等

### 组合查询

```mysql
-- sql查询1
UNION ALL
-- sql查询2
```

组合两个查询，互相独立

### 分页查询

```mysql
SELECT 字段列表 FROM 表名 LIMIT 起始索引, 查询记录数;
```

注意事项: 

• 起始索引从0开始，起始索引 = （查询页码 - 1）* 每页显示记录数。 

• **分页查询是数据库的方言，不同的数据库有不同的实现**，MySQL中是LIMIT。 

• 如果查询的是第一页数据，起始索引可以省略，直接简写为 limit 10

## DCL

DCL英文全称是Data Control Language(数据控制语言)，用来管理数据库用户、控制数据库的访问权限。

```mysql
-- 查询用户
select * from mysql.user;
-- 创建用户
CREATE USER '用户名'@'主机名' IDENTIFIED BY '密码';
-- 修改用户密码
ALTER USER '用户名'@'主机名' IDENTIFIED WITH mysql_native_password BY '新密码';
-- 删除用户
DROP USER '用户名'@'主机名';

```

其中 **Host**代表当前用户访问的主机, 如果为localhost, 仅代表只能够在当前本机访问，是不可以 远程访问的。 **User代表的是访问该数据库的用户名**。在MySQL中需要通过Host和User来唯一标识一 个用户。

注意事项: 

• 在MySQL中需要通过用户名@主机名的方式，来唯一标识一个用户。

• 主机名可以使用 % 通配。 

• 这类SQL开发人员操作的比较少，主要是DBA（ Database Administrator 数据库 管理员）使用。

![MySQL7](C:\Users\25798\OneDrive\Notes\image\MySQL7.png)

```mysql
-- 查询权限
SHOW GRANTS FOR '用户名'@'主机名';
-- 授予权限
GRANT 权限列表 ON 数据库名.表名 TO '用户名'@'主机名';
-- 撤销权限
REVOKE 权限列表 ON 数据库名.表名 FROM '用户名'@'主机名';
/* 注意事项：
• 多个权限之间，使用逗号分隔
• 授权时， 数据库名和表名可以使用 * 进行通配，代表所有。*/
```

# 函数

## 字符串函数

![MySQL8](C:\Users\25798\OneDrive\Notes\image\MySQL8.png)

## 数值函数

![MySQL9](C:\Users\25798\OneDrive\Notes\image\MySQL9.png)

## 日期函数

![MySQL10](C:\Users\25798\OneDrive\Notes\image\MySQL10.png)

## 流程函数

![MySQL11](C:\Users\25798\OneDrive\Notes\image\MySQL11.png)

# 约束

**概念**：约束是作用于表中字段上的规则，用于限制存储在表中的数据。 

**目的**：保证数据库中数据的正确、有效性和完整性。

![MySQL12](C:\Users\25798\OneDrive\Notes\image\MySQL12.png)

注意：约束是作用于表中字段上的，可以在创建表/修改表的时候添加约束。

## 外键约束

外键：用来让两张表的数据之间建立连接，从而保证数据的一致性和完整性。

两张表，只是在逻辑上存在这样一层关系；在数据库层面，并未建立外键关联， 所以是无法保证**数据的一致性和完整性**的。

**添加外键**

```mysql
CREATE TABLE 表名(
字段名 数据类型,
 ...
[CONSTRAINT] [外键名称] FOREIGN KEY (外键字段名) REFERENCES 主表 (主表列名)
);
-- -------
ALTER TABLE 表名 ADD CONSTRAINT 外键名称 FOREIGN KEY (外键字段名) 
REFERENCES 主表 (主表列名) ;
```

**删除外键**

```mysql
ALTER   TABLE  表名   DROP  FOREIGN  KEY  外键名称;
```

**删除/更新行为** 

添加了外键之后，再删除父表数据时产生的约束行为，我们就称为删除/更新行为。具体的删除/更新行 为有以下几种:

![MySQL13](C:\Users\25798\OneDrive\Notes\image\MySQL13.png)

**在一般的业务系统中，不会修改一张表的主键值**

# 多表查询

表结构之间也存在着各种联系，基本上分为三种： 

一对多(多对一) 、多对多 、一对一

```mysql
select * from 表1, 表2；
```

获取结果集为多表的**笛卡尔积**。

```mysql
select * from 表1, 表2 where 表1.id = 表2.id；
```

要where条件限制，来限制结果。

## 连接查询

内连接：相当于查询A、B交集部分数据 

外连接： 

左外连接：查询左表所有数据，以及两张表交集部分数据 

右外连接：查询右表所有数据，以及两张表交集部分数据 

自连接：当前表与自身的连接查询，自连接必须使用表别名

**内连接**

```mysql
-- 隐式内连接
SELECT 字段列表 FROM 表1,表2 WHERE 条件 ... ;
-- 显式内连接
SELECT 字段列表 FROM 表1 [ INNER ] JOIN 表2 ON 连接条件 ... ;
```

一旦为表起了别名，就不能再使用表名来指定对应的字段了，此时只能够使用别名来指定字段。

**外连接**

```mysql
-- 左外连接
SELECT 字段列表 FROM 表1 LEFT [ OUTER ] JOIN 表2 ON 条件 ... ;
-- 右外连接
SELECT 字段列表 FROM 表1 RIGHT [ OUTER ] JOIN 表2 ON 条件 ... ;
```

左外连接和右外连接是可以**相互替换**的，只需要调整在连接查询时SQL中，表结构的先后顺序就可以了。而我们在日常开发使用时，**更偏向于左外连接**。

**自连接**

自己连接自己，也就是把一张表连接查询多次。

```mysql
SELECT 字段列表 FROM 表A 别名A JOIN 表A 别名B ON 条件 ... ;
```

对于自连接查询，可以是内连接查询，也可以是外连接查询。

在**自连接查询**中，**必须要为表起别名**，要不然我们不清楚所指定的条件、返回的字段，到底 是哪一张表的字段。

**联合查询**

对于union查询，就是把多次查询的结果合并起来，形成一个新的查询结果集。

```mysql
SELECT 字段列表 FROM 表A ...  
UNION [ALL]
SELECT 字段列表 FROM 表B ....;
```

对于联合查询的多张表的列数必须保持一致，字段类型也需要保持一致。 

union all 会将全部的数据直接合并在一起，union 会对合并之后的数据去重。

## 子查询

SQL语句中嵌套SELECT语句，称为嵌套查询，又称子查询。

```mysql
SELECT * FROM t1 WHERE column1 = ( SELECT column1 FROM t2 );
```

子查询外部的语句可以是INSERT / UPDATE / DELETE / SELECT 的任何一个。

根据**子查询结果不同**，分为： 

A. 标量子查询（子查询结果为单个值）

B. 列子查询(子查询结果为一列) 

C. 行子查询(子查询结果为一行) 

D. 表子查询(子查询结果为多行多列) 

根据**子查询位置**，分为： 

A. WHERE之后 

B. FROM之后 

C. SELECT之后



# 事务

事务 **是一组操作的集合**，它是一个不可分割的工作单位，事务会把所有的操作作为一个整体一起向系统提交或撤销操作请求，即这些操作要么同时成功，要么同时失败。

需要在业务逻辑执行之前**开启事务**，**执行完毕**后**提交事务**。如果执行过程中**报错**，则**回滚事务**，把数据恢复到事务开始之前的状态。

默认MySQL的事务是自动提交的，也就是说，当执行完一条**DML**语句时，MySQL会立即**隐式的提交事务**。

```mysql
-- 查看/设置事务提交方式
SELECT @@autocommit;
SET @@autocommit = 0; -- 关闭自动提交事务

-- 开启事务
START TRANSACTION 或 BEGIN;
-- 提交事务
COMMIT;
-- 回滚事务
ROLLBACK;
```

**事务四大特性**  

1、原子性（Atomicity）：事务是不可分割的最小操作单元，要么全部成功，要么全部失败。 

2、一致性（Consistency）：事务完成时，必须使所有的数据都保持一致状态。 

3、隔离性（Isolation）：**数据库系统提供的隔离机制**，保证事务在不受外部并发操作影响的独立环境下运行。

4、持久性（Durability）：事务一旦提交或回滚，它对数据库中的数据的改变就是永久的。 

上述就是事务的四大特性，简称ACID。

### 并发事务问题

1、赃读：一个事务读到另外一个事务还没有提交的数据。

2、不可重复读：**一个事务先后读取同一条记录**，但两次读取的数据不同，称之为不可重复读。

3、幻读：一个事务按照条件查询数据时，没有对应的数据行，但是在插入数据时，又发现这行数据已经存在，好像出现了 "幻影"。

### 事务隔离级别

![MySQL14](C:\Users\25798\OneDrive\Notes\image\MySQL14.png)

```mysql
-- 查看事务隔离级别
SELECT @@TRANSACTION_ISOLATION;
-- 设置事务隔离级别
SET [ SESSION | GLOBAL ] TRANSACTION ISOLATION LEVEL {READ UNCOMMITTED | READ COMMITTED | REPEATABLE READ | SERIALIZABLE}
```

事务隔离级别越高，数据越安全，但是性能越低。

