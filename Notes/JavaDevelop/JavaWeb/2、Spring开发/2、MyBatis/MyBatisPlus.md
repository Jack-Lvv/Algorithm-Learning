# MyBatisPlus

[TOC]

无侵入，方便快捷，功能增强

## 快速使用

1、引入依赖，包括了基础MyBatis

```xml
<dependency>
  <groupId>com.baomidou</groupId>
  <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
  <version>3.5.9</version>
</dependency>
```

2、Mapper继承BaseMapper<实体类> 即可使用

```java
public interface UserMapper extends BaseMapper<User> {
    
}
```



## 常见注解

MBP通过扫描实体类，基于反射获取实体类信息作为数据库表信息，名称驼峰转下划线，id为主键

不符合约定的部分需要进行下面指定：

@TableName("...")：指定表名

@Tabled("...")：指定表中的主键字段

@TableField("...")：指定表中的普通字段

@TableField(exist = false) ：不是数据库中的字段

IdType：数据库自增、执行set、雪花算法(默认)

布尔类型含is需要指定

```yaml
mybatis-plus:
	type-aliases-package: com.itheima.mp.domain.po # 别名扫描包
	mapper-locations: "classpath*:/mapper/**/*.xml" # Mapper.xml文件地址，默认值
	configuration:
		map-underscore-to-camel-case: true # 是否开启下划线和驼峰的映射
		cache-enabled: false # 是否开启二级缓存
  global-config:
  	db-config:
  		id-type: assign_id # id为雪花算法生成
  		update-strategy: not_null # 更新策略：只更新非空字段
```

[MyBatis-Plus 🚀 为简化开发而生](https://www.baomidou.com/)

## 条件构造器

```java
void query() {
    QueryWrapper<User> wrapper = new QueryWrapper<User>()
        .select("id", "username", "info", "balance")
        .like("username", "o")
        .ge("balance", 1000); // 类sql链式调用
    userMapper.selectList(wrapper);
}
```

## Service接口

1、自定义Service接口继承IService接口

2、自定义Service实现类，实现自定义接口并继承ServiceImpl类