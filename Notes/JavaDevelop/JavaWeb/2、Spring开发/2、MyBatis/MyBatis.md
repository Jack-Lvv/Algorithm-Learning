# MyBatis

[TOC]

MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。 

MyBatis 不像 Hibernete 等这些全自动框架，它把关键的SQL部分交给程序员自己编写，而不是自动生成。



1、**安装MyBatisx插件自动生成xml文件**

2、将原来的Dao替换成Mapper接口，接口@Mapper注解，通过xml文件中的sql实现数据库操作

```xml
<select id="getEmpById" resultType="全类名">
    select ... from emp where id = #{id}
</select>
```

3、项目配置文件：

```properties
mybatis.mapper-locations=classpath:mapper/**.xml
```

内部MyBatis根据xml文件为Mapper接口创建代理对象

数据库添加过程中id自增

```xml
<insert id="addEmp" useGeneratedKeys="true" keyProperty="id">
   ...
</insert>
<!--
将自增id回填到数据库中
useGeneratedKeys：jdbc规范中提供了此设置，mybatis对此也进行了实现
keyProperty：指定主键的属性名
-->
```

**项目配置中开启驼峰命名自动映射**

```properties
mybatis.configuration.map-underscore-to-camel-case=true
# empId 和数据库中 emp_id 可以自动匹配
```

# CRUD

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.atguigu.mapper.EmployeeMapper">
    <select id="getEmp" resultType="com.atguigu.mybatis.entity.Employee">
        select * from `t_emp` where id = #{id}
    </select>
    <select id="getAllEmp" resultType="com.atguigu.mybatis.entity.Employee">
        select * from `t_emp`
    </select>
    
    <insert id="saveEmp">
        insert into t_emp(emp_name,emp_salary) values(#{empName},#{empSalary})
    </insert>
    <update id="updateEmp">
        update t_emp set emp_salary=#{empSalary} where emp_id=#{empId}
    </update>
    <delete id="deleteEmp">
        delete from t_emp where id = #{id}
    </delete>
</mapper>
```

# 参数传递

**#{}**：底层使用 PreparedStatement 方式，SQL预编译后设置参数，无SQL注入攻击风险

**${}**：底层使用 Statement 方式，SQL无预编译，直接拼接参数，有SQL注入攻击风险(可以拼接成SQL语句)

1、所有参数位置，都应该用 #{}

2、需要动态表名等，JDBC不支持预编译的位置，才用 ${}

**最佳实践：**

凡是使用了 ${}  的业务，一定要自己编写防SQL注入攻击代码

| **传参形式**         | **示例**                                                     | **取值方式**                                                 |
| -------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 单个参数 - 普通类型  | getEmploy(Long id)                                           | #{变量名}                                                    |
| 单个参数 -  List类型 | getEmploy(List<Long> id)                                     | #{变量名[0]}                                                 |
| 单个参数 - 对象类型  | addEmploy(Employ e)                                          | #{对象中属性名}                                              |
| 单个参数 -  Map类型  | addEmploy(Map<String,Object> m)                              | #{map中属性名}                                               |
| 多个参数 - 无@Param  | getEmploy(Long id,String name)                               | #{变量名} //新版兼容                                         |
| 多个参数 - 有@Param  | getEmploy(@Param(“id”)Long  id,  @Param(“name”)String  name) | #{param指定的名}                                             |
| 扩展：               | getEmploy(@Param(“id”)Long  id,  @Param(“ext”)Map<String,Object>  m,  @Param(“ids”)List<Long>  ids,  @Param(“emp”)Employ  e) | #{id}、  #{ext.name}、#{ext.age}，  #{ids[0]}、#{ids[1]}，  #{e.email}、#{e.age} |

**多个参数用 @Param 指定参数名**

# 返回结果类型

MyBatis 为 java.lang 下的很多数据类型都起了别名，只需要用Long，String，Double 等这些表示即可，不用写全类名

```xml
<select id="getEmp" resultType="com.atguigu.mybatis.entity.Employee">
     select * from `t_emp` where id = #{id}
</select>
<select id="countEmp" resultType="long">
    select count(*) from `t_emp`
</select>
```

| 别名                      | Java类型  |
| ------------------------- | --------- |
| _byte                     | byte      |
| _char (since 3.5.10)      | char      |
| _character (since 3.5.10) | char      |
| _long                     | long      |
| _short                    | short     |
| _int                      | int       |
| _integer                  | int       |
| _double                   | double    |
| _float                    | float     |
| _boolean                  | boolean   |
| string                    | String    |
| byte                      | Byte      |
| char (since 3.5.10)       | Character |
| character (since 3.5.10)  | Character |

| **别名**     | Java类型     |
| ------------ | ------------ |
| long         | Long         |
| short        | Short        |
| int          | Integer      |
| integer      | Integer      |
| double       | Double       |
| float        | Float        |
| boolean      | Boolean      |
| date         | Date         |
| decimal      | BigDecimal   |
| bigdecimal   | BigDecimal   |
| biginteger   | BigInteger   |
| object       | Object       |
| date[]       | Date[]       |
| decimal[]    | BigDecimal[] |
| bigdecimal[] | BigDecimal[] |
| biginteger[] | BigInteger[] |

| **别名**   | Java类型   |
| ---------- | ---------- |
| object[]   | Object[]   |
| map        | Map        |
| hashmap    | HashMap    |
| list       | List       |
| arraylist  | ArrayList  |
| collection | Collection |
| iterator   | Iterator   |

**如果返回集合，resultType写集合中的元素类型**

数据库的字段 如果和 Bean的属性 不能一一对应

1、如果符合驼峰命名，则开启驼峰命名规则

2、编写自定义结果集（ResultMap） 进行封装

```xml
<resultMap id="EmpResultMap" type="com.atguigu.mybatis.entity.Employee">
    <id column="emp_id" property="empId"/>
    <result column="emp_name" property="empName"/>
    <result column="emp_salary" property="empSalary"/>
</resultMap>
<!--
自定义封装结果集并使用
-->
<select id="getEmp" resultMap="EmpResultMaps">
     select * from `t_emp` where id = #{id}
</select>
```

id 标签：必须指定主键列映射规则

result 标签：指定普通列映射规则

collection 标签：指定自定义集合封装规则(对多)

association 标签：指定自定义对象封装规则(对一)

## 数据关联关系

1-1：一对一；多表联查产生一对一关系，比如一个订单对应唯一一个下单客户；此时需要**保存关系键到某个表中**

1-N：一对多；多表联查产生一对多关系，比如一个客户产生了多个订单记录；此时**多的一端需要保存关系键到自己表中**

N-N：多对多：无论从哪端出发看，都是对多关系，这就是一个多对多的关系，比如 一个学生有多个老师、一个老师又教了多个学生；此时需要**一个中间表记录学生和老师的关联关系**

```xml
<resultMap id="EmpResultMap" type="com.atguigu.mybatis.entity.Employee">
    <id column="emp_id" property="empId"/>
    <result column="emp_name" property="empName"/>
    <result column="emp_salary" property="empSalary"/>
    <association property="封装对象名" javaType="封装对象全类名">
        <!--
			对应封装对象，同上
		-->
    </association>
    <collection property="封装对象名" ofType="封装对象全类名">
        <!--
			对应封装对象集合，同上
		-->
    </collection>
</resultMap>
```

**association 标签**：指定自定义对象封装规则，一般用户联合查询一对一关系的封装。比如一个用户对应一个订单

•javaType：指定关联的Bean的类型

•select：指定分步查询调用的方法

•column：指定分步查询传递的参数列

**collection 标签**：指定自定义对象封装规则，一般用户联合查询一对多关系的封装。比如一个用户对应多个订单

•ofType：指定集合中每个元素的类型

•select：指定分步查询调用的方法

column：指定分步查询传递的参数列

在 association 和 collection 的封装过程中，可以使用 select + column 指定**分步查询逻辑**

•select：指定分步查询调用的方法

•column：指定分步查询传递的参数

•传递单个：直接写列名，表示将这列的值作为参数传递给下一个查询

•传递多个：column="{prop1=col1,prop2=col2}"，下一个查询使用prop1、prop2取值

**分步查询注意不要循环查询，会爆栈**

**分步查询** 有时候并不需要立即运行，我们希望在用到的时候再去查询，可以开启**延迟加载**的功能，在配置文件中配置

```properties
mybatis.configuration.lazy-loading-enabled=true
mybatis.configuration.aggressive-lazy-loading=false
```

# 动态SQL

动态 SQL 是 MyBatis 的强大特性之一。根据不同条件拼接 SQL 语句。

**where标签解决拼接过程语法问题，set标签同理**

还可以用**trim标签**解决

```xml
    <select id="selectEmployeeByCondition" resultType="employee">
        select emp_id,emp_name,emp_salary from t_emp
        <where>
            <if test="empName != null">
                or emp_name=#{empName}
            </if>
            <if test="empSalary !=null">
                or emp_salary>#{empSalary}
            </if>
        </where>
    </select>
```

```xml
<update id="updateEmployeeDynamic">
        update t_emp
        <set>
            <if test="empName != null">
                emp_name=#{empName},
            </if>
            <if test="empSalary &lt; 3000">
                emp_salary=#{empSalary},
            </if>
        </set>
        where emp_id=#{empId}
    </update>
```

**choose/when/otherwise标签**，在多个分支条件中，仅执行一个。

```xml
    <select id="selectEmployeeByConditionByChoose" 
                  resultType="com.atguigu.mybatis.entity.Employee">
        select emp_id,emp_name,emp_salary from t_emp where
        <choose>
            <when test="empName != null">emp_name=#{empName}</when>
            <when test="empSalary &lt; 3000">emp_salary &lt; 3000</when>
            <otherwise>1=1</otherwise>
        </choose>
    </select>
```

**foreach 标签**

用来遍历，循环；常用于批量插入场景；批量单个SQL

注意对象不能为空，嵌套一个if判空标签

```xml
<!--
    collection属性：要遍历的集合
    item属性：遍历集合的过程中能得到每一个具体对象，在item属性中设置一个名字，将来通过这个名字引用遍历出来的对象
    separator属性：指定当foreach标签的标签体重复拼接字符串时，各个标签体字符串之间的分隔符
    open属性：指定整个循环把字符串拼好后，字符串整体的前面要添加的字符串
    close属性：指定整个循环把字符串拼好后，字符串整体的后面要添加的字符串
    index属性：这里起一个名字，便于后面引用
        遍历List集合，这里能够得到List集合的索引值
        遍历Map集合，这里能够得到Map集合的key
 -->
<foreach collection="empList" item="emp" separator="," open="values" index="myIndex">
    (#{emp.empName},#{myIndex},#{emp.empSalary},#{emp.empGender})
</foreach>
```

**批量多个SQL**，allowMultiQueries：允许多个SQL用;隔开，批量发送给数据库执行

```properties
jdbc:mysql:///mybatis-example?allowMultiQueries=true
```

可以支持**事务**的多SQL批量操作的**回滚**，但**分布式事务**大多不支持

```xml
    <update id="updateEmployeeBatch">
        <foreach collection="empList" item="emp" separator=";">
            update t_emp set emp_name=#{emp.empName} where emp_id=#{emp.empId}
        </foreach>
    </update>
```

抽取可以复用的SQL片段

```xml
    <sql id="empColumn">
         emp_id,emp_name,emp_age,emp_salary,emp_gender
    </sql>

    <select id="getEmp" resultType="com.atguigu.mybatis.entity.Employee">
        select
            <include refid="empColumn"/>
        from `t_emp` where id = #{id}
    </select>
```

## 特殊字符

在xml中，以下字符需要用转义字符，不能直接写

| **原始字符** | **转义字符** |
| ------------ | ------------ |
| &            | &amp;        |
| <            | &lt;         |
| >            | &gt;         |
| "            | &quot;       |
| '            | &apos;       |

# 缓存机制

MyBatis 拥有二级缓存机制 (基本用redis替代)：

1）一级缓存默认开启； 事务级别：**当前事务共享**

缓存不命中：1、首次访问 2、查询数据缓存后经过变更操作，需要重新查询

缓存命中就不用查数据库；

2）二级缓存需要**手动配置**开启：**所有事务共享** 

​	**先访问二级缓存，再访问一级缓存，最后查数据库。**

​	L1~LN：N级缓存

​	数字越小离我越近，查的越快。存储越小，造价越高。

​	数字越大离我越远，查的越慢。存储越大，造价越低。

​	在xml数据库操作中添加< cache\ >标签即可开启二级缓存，但是对应bean类要实现序列号接口： Serializable，二级缓存是将数据序列化后存入磁盘

# 插件机制

MyBatis 底层使用 **拦截器机制**提供插件功能，方便用户在SQL执行前后进行拦截增强。

拦截器：**Interceptor**

拦截器可以拦截 四大对象 的执行

1）ParameterHandler：处理SQL的参数对象

2）ResultSetHandler：处理SQL的返回结果集

3）StatementHandler：数据库的处理对象，用于执行SQL语句

4）Executor：MyBatis的执行器，用于执行增删改查操作

# PageHelper 分页插件

**PageHelper** 是可以用在 MyBatis 中的一个强大的**分页插件**

分页插件就是利用MyBatis 插件机制，在底层编写了 **分页Interceptor**，每次SQL查询之前会自动拼装分页数据

select * from emp limit 0,10

•前端 第1页： limit 0,10

•前端 第2页： limit 10,10

•前端 第3页： limit 20,10

•前端 第N页：limit startIndex,pageSize

计算规则： pageNum = 1， pageSize = 10

startIndex = (pageNum - 1)*pageSize

1）导入依赖

```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.4.7</version>
</dependency>
```



2）注入PageInterceptor组件

```java
PageHelper.startPage(1,5); // pageNum, pageSize
// 紧跟着的方法就会执行SQL分页查询
```

# mybatisx 逆向生成

IDEA 安装 **mybatisx 插件**，即可根据数据库表一键生成常用CRUD

```java
@MapperScan("包名.文件夹名") // 自动加上Mapper注解进行注入
```

