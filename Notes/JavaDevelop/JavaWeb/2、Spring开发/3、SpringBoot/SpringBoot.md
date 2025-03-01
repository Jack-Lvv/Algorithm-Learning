# SpringBoot

**特性**：

•快速创建独立 Spring 应用

•直接嵌入Tomcat、Jetty or Undertow

•提供可选的 starter，简化应用整合

•按需自动配置 Spring 以及 第三方库

•提供生产级特性：如 监控指标、健康检查、外部化配置等

•无代码生成、无xml； 都是基于**自动配置**技术

**总结**：

简化开发，简化配置，简化整合，简化部署，简化监控，简化运维

## 快速部署

```xml
<!--    SpringBoot应用打包插件-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

```cmd
mvn clean package
java -jar demo.jar // 直接jar包运行
```

## 场景启动器

场景启动器：导入相关的场景jar包，拥有相关的功能。

默认支持的所有场景：

•https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters

官方提供的场景：命名为：spring-boot-starter-*

第三方提供场景：命名为：*-spring-boot-starter

所有启动器的基础启动器：spring-boot-starter

## 版本管理

SpringBoot通过Maven的父项目依赖规定了版本号，大部分都无需写版本号

父项目没规定的需要自己指定版本，子项目自己规定后可以覆盖父项目的版本

## 自动配置

约定大于配置：大量的默认配置，不需要配置

1、导入场景，容器中就会自动配置好这个场景的核心组件。

•如 Tomcat、SpringMVC、DataSource 等

•不喜欢的组件可以自行配置进行替换

2、默认的包扫描规则

•SpringBoot只会扫描主程序所在的包及其下面的子包

3、配置默认值

•配置文件的所有配置项 是和 某个类的对象值进行一一绑定的。

•很多配置即使不写都有默认值，如：端口号，字符编码等

•默认能写的所有配置项：https://docs.spring.io/spring-boot/appendix/application-properties/index.html

4、按需加载自动配置

•导入的场景会导入全量自动配置包，但并不是都生效

### 完整流程

1、导入 starter，就会导入autoconfigure 包。

2、autoconfigure 包里面 有一个文件 META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports,里面指定的所有启动要加载的自动配置类

3、@EnableAutoConfiguration 会自动的把上面文件里面写的所有自动配置类都导入进来。xxxAutoConfiguration 是有条件注解进行按需加载

4、xxxAutoConfiguration 给容器中导入一堆组件，组件都是从 xxxProperties 中提取属性值

5、xxxProperties 又是和配置文件进行了绑定

导入starter、修改配置文件，就能修改底层行为。

![](C:\Users\25798\OneDrive\Notes\image\SpringBoot1.png)

## 基础功能

### 属性绑定

将容器中任意组件的属性值和配置文件的配置项的值进行绑定

1、给容器中注册组件（@Component、@Bean）

2、使用 **@ConfigurationProperties(prefix = "前缀匹配名")**声明组件和配置文件的哪些配置项进行绑定

或者：全局写@EnableConfigurationProperties(类名.class) 自动完成上面两步

### YAML文件

痛点：SpringBoot 集中化管理配置，application.properties

​	•问题：配置多以后难阅读和修改，层级结构辨识度不高

YAML: YAML Ain't Markup Language™

​	•设计目标，就是方便人类读写

​	•层次分明，更适合做配置文件

​	•使用.yaml或 .yml作为文件后缀

基本语法：

•大小写敏感

•键值对写法 k: v，使用空格分割k,v

•使用**缩进表示层级关系**

​	•缩进时不允许使用Tab键，只允许使用空格。换行

​	•缩进的空格数目不重要，只要相同层级的元素左侧对齐即可

•# 表示注释，从这个字符一直到行尾，都会被解析器忽略。

•Value支持的写法

​	•对象：键值对的集合，如：映射（map）/ 哈希（hash） / 字典（dictionary）

​	•数组：一组按次序排列的值，如：序列（sequence） / 列表（list）

​	•字面量：单个的、不可再分的值，如：字符串、数字、bool、日期

<img src="C:\Users\25798\OneDrive\Notes\image\SpringBoot3.png" alt="SpringBoot2" style="zoom:50%;" />

**YAML和properties可以同时使用，配置互补，有冲突时以properties优先**

### banner

SpringBoot启动时的logo，默认配置为：

```properties
spring.banner.location=classpath:banner.txt
```

### SpringApplication

•new SpringApplication

可以在启动应用时对应用进行配置，banner设置、监听器设置、环境设置等

•new SpringApplicationBuilder

可以链式调用，用法同上

## 日志

SpringBoot默认：SLF4J日志门面、Logback日志实现

Log4j2性能较好但是兼容性差

SpringBoot基础启动类已经默认导入了日志场景

也可以使用原始的对应日志的配置文件

```java
Logger logger = LoggerFactory.getLogger(类名.class);
logger.trace("底层追踪日志...");
logger.debug("调试日志...");
logger.info("信息日志...");
logger.warn("警告日志...");
logger.error("错误日志...");
// --------------
@Slf4j
public class Log {
    log.warn("日志...");
    // Slf4j注解直接使用log进行记录日志
}
```

默认输出格式：

1、时间和日期：毫秒级精度

2、日志级别：ERROR, WARN, INFO, DEBUG, or TRACE.

3、进程 ID

4、---： 消息分割符

5、线程名： 使用[]包含

6、Logger 名： 通常是产生日志的类名

7、消息： 日志记录的内容

### 日志级别

由低到高：ALL,TRACE, DEBUG, INFO, WARN, ERROR,FATAL,OFF；

只会打印指定级别及以上级别的日志

•ALL：打印所有日志

•TRACE：追踪框架详细流程日志，一般不使用

•DEBUG：开发调试细节日志

•INFO：关键、感兴趣信息日志

•WARN：警告但不是错误的信息日志，比如：版本过时

•ERROR：业务错误日志，比如出现各种异常

•FATAL：致命错误日志，比如jvm系统崩溃

•OFF：关闭所有日志记录

不指定级别的所有类，都使用 root 指定的级别作为默认级别

SpringBoot日志默认(打印)级别是 **INFO**

```properties
# 修改默认(打印)级别为debug
logging.level.root=debug
# 修改单独一个包的默认(打印)级别为info
logging.level.包名=info

# 设置日志组
logging.group.biz=包1,包2...
# 批量设置
logging.level.biz=debug

# 修改格式
logging.pattern.console=
logging.pattern.file=
```

SpringBoot 预定义两个组：

| 组名 | 范围                                                         |
| ---- | ------------------------------------------------------------ |
| web  | org.springframework.core.codec,  org.springframework.http,   org.springframework.web,  org.springframework.boot.actuate.endpoint.web, org.springframework.boot.web.servlet.ServletContextInitializerBeans |
| sql  | org.springframework.jdbc.core,   org.hibernate.SQL,   org.jooq.tools.LoggerListener |

### 文件输出

SpringBoot 默认只把日志写在控制台，如果想额外记录到文件，可以在application.properties中添加  logging.file.name 或 logging.file.path 配置项。

| **logging.file.name** | **logging.file.path** | **示例** | **效果**                         |
| --------------------- | --------------------- | -------- | -------------------------------- |
| 未指定                | 未指定                |          | 仅控制台输出                     |
| **指定**              | 未指定                | my.log   | 写入指定文件。可以加路径         |
| 未指定                | **指定**              | /var/log | 写入指定目录，文件名为spring.log |
| **指定**              | **指定**              |          | 以logging.file.name为准          |

### 文件归档与滚动切割

**归档**：每天的日志单独存到一个文档中。

**切割**：每个文件10MB，超过大小切割成另外一个文件。

默认**滚动切割**与归档规则如下：

| **配置项**                                               | **描述**                                                     |
| -------------------------------------------------------- | ------------------------------------------------------------ |
| logging.**logback**.rollingpolicy.file-name-pattern      | 日志存档的文件名格式  默认值：${LOG_FILE}.%d{yyyy-MM-dd}.%i.gz |
| logging.**logback**.rollingpolicy.clean-history-on-start | 应用启动时是否清除以前存档；默认值：false                    |
| logging.**logback**.rollingpolicy.max-file-size          | 每个日志文件的最大大小；默认值：10MB                         |
| logging.**logback**.rollingpolicy.total-size-cap         | 日志文件被删除之前，可以容纳的最大大小（默认值：0B）。设置1GB则磁盘存储超过 1GB 日志后就会删除旧日志文件，0表示不限制大小。 |
| logging.**logback**.rollingpolicy.max-history            | 日志文件保存的最大天数；默认值：7                            |

切换为其他日志：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <exclusions>
        <exclusion>
        <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

### 总结

1、导入任何第三方框架，先排除它的日志包，因为Boot底层控制好了日志

2、修改 application.properties 配置文件，就可以调整日志的所有行为。如果不够，可以编写日志框架自己的配置文件放在类路径下就行，比如logback-spring.xml，log4j2-spring.xml

3、如需对接专业日志系统，也只需要把 logback 记录的日志灌到 kafka之类的中间件，这和SpringBoot没关系，都是日志框架自己的配置，修改配置文件即可

4、业务中使用slf4j-api记录日志。

## 进阶功能

### Profiles环境隔离

环境隔离能力；快速切换**开发、测试、生产**环境

1、标识环境：指定哪些组件、配置在哪个环境生效

​	@Profile("环境标识") 标记组件生效环境

​	使用配置文件命名来配置不同环境下的设置：application-dev.properties // 开发环境配置

2、切换环境：这个环境对应的所有组件和配置就应该**生效**

​	激活环境：

​		配置文件：spring.profiles.active=production,hsqldb

​		命令行：java -jar demo.jar --spring.profiles.active=dev,hsqldb

​		**命令行启动时可以使用--后加任意配置设置来定义配置项**

​	配置文件可以设置包含的环境：

​		spring.profiles.include[0]=common

​		spring.profiles.include[1]=local

​	生效的配置 = 默认环境配置 + 激活的环境 + 包含的环境配置

**激活的配置优先级高，profile配置支持分组**

•基础的配置mybatis、log、xxx：写到包含环境中

•需要动态切换变化的 db、redis：写到激活的环境中

### 外部化配置

**外部配置优先于内部配置**，内部配置 < 外部配置 < config文件夹 < config文件夹中的子文件夹

**命令行配置优先级最高**

直接在jar包外放置配置文件，可以直接读取，便于修改

属性占位符：

​	app.name=MyApp

​	app.description=${app.name} Hello

**激活优先大于外部优先**

### 单元测试

@Test :表示方法是测试方法。

@ParameterizedTest :表示方法是参数化测试

@RepeatedTest :表示方法可重复执行

@DisplayName :为测试类或者测试方法设置展示名称

@BeforeEach :表示在每个单元测试之前执行

@AfterEach :表示在每个单元测试之后执行

@BeforeAll :表示在所有单元测试之前执行

@AfterAll :表示在所有单元测试之后执行

@Tag :表示单元测试类别，类似于JUnit4中的@Categories

@Disabled :表示测试类或测试方法不执行，类似于JUnit4中的@Ignore

@Timeout :表示测试方法运行如果超过了指定时间将会返回错误

@ExtendWith :为测试类或测试方法提供扩展类引用

| **方法**          | **说明**                             |
| ----------------- | ------------------------------------ |
| assertEquals      | 判断两个对象或两个原始类型是否相等   |
| assertNotEquals   | 判断两个对象或两个原始类型是否不相等 |
| assertSame        | 判断两个对象引用是否指向同一个对象   |
| assertNotSame     | 判断两个对象引用是否指向不同的对象   |
| assertTrue        | 判断给定的布尔值是否为  true         |
| assertFalse       | 判断给定的布尔值是否为  false        |
| assertNull        | 判断给定的对象引用是否为  null       |
| assertNotNull     | 判断给定的对象引用是否不为  null     |
| assertArrayEquals | 数组断言                             |
| assertAll         | 组合断言                             |
| assertThrows      | 异常断言                             |
| assertTimeout     | 超时断言                             |
| fail              | 快速失败                             |

### 可观测性

可观测性（Observability）指应用的运行数据，可以被线上进行观测、监控、预警等

1、SpringBoot 提供了 actuator 模块，可以快速暴露应用的所有指标

2、导入： spring-boot-starter-actuator

3、展示出所有可以用的监控端点

配置文件中：management.endpoints.web.exposure.include=* # 暴露所有指标

访问 http://ip:8080/actuator 即可观测

| **端点名**       | **描述**                                                     |
| ---------------- | ------------------------------------------------------------ |
| auditevents      | 暴露当前应用程序的审核事件信息。需要一个AuditEventRepository组件 |
| beans            | 显示应用程序中所有Spring  Bean的完整列表                     |
| caches           | 暴露可用的缓存                                               |
| conditions       | 显示自动配置的所有条件信息，包括匹配或不匹配的原因           |
| configprops      | 显示所有@ConfigurationProperties                             |
| env              | 暴露Spring的属性ConfigurableEnvironment                      |
| flyway           | 显示已应用的所有Flyway数据库迁移。需要一个或多个Flyway组件。 |
| health           | 显示应用程序运行状况信息                                     |
| httptrace        | 显示HTTP跟踪信息（默认情况下，最近100个HTTP请求-响应）。需要一个HttpTraceRepository组件 |
| info             | 显示应用程序信息                                             |
| integrationgraph | 显示Spring integrationgraph 。需要依赖spring-integration-core |
| loggers          | 显示和修改应用程序中日志的配置                               |
| liquibase        | 显示已应用的所有Liquibase数据库迁移。需要一个或多个Liquibase组件。 |
| **metrics**      | **显示当前应用程序的“指标”信息**                             |

| **端点名**     | **描述**                                                     |
| -------------- | ------------------------------------------------------------ |
| mappings       | 显示所有@RequestMapping路径列表                              |
| scheduledtasks | 显示应用程序中的计划任务                                     |
| sessions       | 允许从Spring  Session支持的会话存储中检索和删除用户会话。需要使用Spring  Session的基于Servlet的Web应用程序 |
| shutdown       | 使应用程序正常关闭。默认禁用                                 |
| startup        | 显示由ApplicationStartup收集的启动步骤数据。需要使用SpringApplication进行配置BufferingApplicationStartup |
| **threaddump** | **执行线程转储**                                             |
| **heapdump**   | **返回hprof堆内存转储文件**                                  |
| jolokia        | 通过HTTP暴露JMX bean（需要引入Jolokia，不适用于WebFlux）。需要引入依赖jolokia-core |
| logfile        | 返回日志文件的内容（如果已设置logging.file.name或logging.file.path属性）。支持使用HTTPRange标头来检索部分日志文件的内容 |
| **prometheus** | **以Prometheus服务器可以抓取的格式公开指标。需要依赖micrometer-registry-prometheus** |

### 生命周期

![SpringBoot4](C:\Users\25798\OneDrive\Notes\image\SpringBoot4.png)

每个生命周期都会发送一个**事件**。监听器：SpringApplicationRunListener，需要配置在资源文件夹META-INF中的spring.factories中 全类名=自己实现的类名

| **监听器**                    | **感知阶段**     | **配置方式**                                                 |
| ----------------------------- | ---------------- | :----------------------------------------------------------- |
| BootstrapRegistryInitializer  | 引导初始化       | 1、META-INF/spring.factories  2、application.addBootstrapRegistryInitializer() |
| ApplicationContextInitializer | ioc容器初始化    | 1、META-INF/spring.factories  2、application.addInitializers() |
| **ApplicationListener**       | **全阶段**       | **1、META-INF/spring.factories  2、SpringApplication.addListeners(…)  3、@Bean 或 @EventListener** |
| SpringApplicationRunListener  | 全阶段           | META-INF/spring.factories                                    |
| **ApplicationRunner**         | **感知应用就绪** | **@Bean**                                                    |
| **CommandLineRunner**         | **感知应用就绪** | **@Bean**                                                    |

![SpringBoot5](C:\Users\25798\OneDrive\Notes\image\SpringBoot5.png)

### 事件驱动开发

应用启动过程生命周期事件感知（9大事件）

应用运行中事件感知（无数种）

事件驱动开发

​	定义事件：

​		任意事件：任意类可以作为事件类，建议命名 xxxEvent

​		系统事件：继承 ApplicationEvent

​	事件发布：

​		组件实现 ApplicationEventPublisherAware

​		自动注入 ApplicationEventPublisher

​	事件监听：

​		组件 + 方法标注@EventListener

![SpringBoot6](C:\Users\25798\OneDrive\Notes\image\SpringBoot6.png)

```java
@Service
public class CouponService {
    @Order(1)
    @Async // 异步多线程监听
    @EventListener(事件类名.class)
    public void onEvent(LoginSuccessEvent loginSuccessEvent){
        System.out.println("===== CouponService ====感知到事件"+loginSuccessEvent);
        UserEntity source = (UserEntity) loginSuccessEvent.getSource();
        sendCoupon(source.getUsername());
    }
    public void sendCoupon(String username){
        System.out.println(username + " 随机得到了一张优惠券");
    }
} // 登录过程调用多

publisher.publishEvent(event);// 发布事件
// 配置类中标注@EnableAsync -- 开启自动异步注解
```

基于事件/消息驱动可以避免方法过多阻塞在一起，同时解耦合作用

本地消息模式，不能在分布式中使用

@Enable-- 注解会自动导入需要的类

![SpringBoot7](C:\Users\25798\OneDrive\Notes\image\SpringBoot7.png)

### 自定义starter

1、创建自定义starter项目，引入spring-boot-starter基础依赖

2、编写模块功能，引入模块所有需要的依赖。

3、编写xxxAutoConfiguration自动配置类，帮其他项目导入这个模块需要的所有组件

**@EnableXxx 机制**

1、编写自定义 @EnableXxx 注解

2、@EnableXxx 导入 自动配置类

**完全自动配置**

1、依赖 SpringBoot 的 SPI 机制

2、META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 文件中编写好我们自动配置类的全类名即可

3、项目启动，自动加载我们的自动配置类
