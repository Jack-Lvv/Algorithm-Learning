[TOC]

# Spring

Spring包括SpringBoot、SpringFramework、SpringCloud等。

狭义的Spring为**SpringFramework**。

Spring是一个 **IOC(DI)** 和 **AOP** 框架



Spring有很多**优良特性**

•非侵入式：基于Spring开发的应用中的对象可以不依赖于Spring的API

•依赖注入：DI（Dependency Injection）是反转控制（IOC）最经典的实现

•面向切面编程：Aspect Oriented Programming - AOP

•容器：Spring是一个容器，包含并管理应用对象的生命周期

•组件化：Spring通过将众多简单的组件配置组合成一个复杂应用。

•一站式：Spring提供了一系列框架，解决了应用开发中的众多问题



Core（核心）：**IoC容器、事件、资源、国际化、数据校验**、数据绑定、类型转换、SpEL、AOP、AOT

Testing（测试）：对象模拟、**测试框架**、SpringMVC测试、WebTestClient

Data Access（数据访问）：**事务**、DAO 支持、JDBC、R2DBC、对象关系映射、XML转换

Web Servlet（Servlet式Web）：**SpringMVC**、WebSocket、SockJS、STOMP 消息

Web Reactive（响应式Web）：Spring WebFlux、WebClient、WebSocket、RSocket

Integration（整合）：REST 客户端、Java消息服务、Java 缓存抽象、Java 管理扩展、邮件、任务、调度、缓存、可观测性、JVM 检查点恢复

# 容器和组件

**组件**：具有一定功能的对象

**容器**：管理组件（创建、获取、保存、销毁）

例如Tomcat就是一个Servlet容器，用来管理Servlet组件

**IoC**：Inversion of Control（控制反转）**思想**

•控制：资源的控制权（资源的创建、获取、销毁等）

•反转：和传统的方式不一样了

**DI** ：Dependency Injection（依赖注入）**实现方法**

•依赖：组件的依赖关系，如 NewsController 依赖 NewsServices

•注入：通过setter方法、构造器、等方式自动的注入（赋值）

| 注册组件的方式       | 用处                |
| -------------------- | ------------------- |
| **@Bean**            | 注册组件到容器中    |
| getBean()            | 组件获取方式        |
| **@Configuration**   | MVC分层模型对应注解 |
| **@Controller**      |                     |
| **@Service**         |                     |
| **@Respository**     |                     |
| **@Component**       |                     |
| **@ComponentScan**   | 批量扫描            |
| **@Import**          | 按需导入            |
| @Scope               | 组件作用域          |
| @Lazy                | 懒加载              |
| FactoryBean          | 工厂Bean            |
| @Conditional【难点】 | 条件注册            |

## @Bean

```java
// 注册组件(即把组件放入容器ioc)，方法名就是组件名，或者用@Bean("name")注解赋名

@Bean
public Person newPerson() {
    Person person = new Person();
    return person;
}

ConfigurableApplicationContext ioc = SpringApplication.run(Spring01IocApplication.class, args);
//获取容器中所有组件的名字
String[] names = ioc.getBeanDefinitionNames();

//获取组件对象
Object obj1 = ioc.getBean("name");
Person bean = ioc.getBean(Person.class);
Person obj2 = (Person)ioc.getBean("name");//强转成对应对象的类
Person bean2 = ioc.getBean("name", Person.class);//不需要强转
```

**组件的四大特性：名字、类型、对象、作用域**

getBean方法获取组件，组件不存在和组件不唯一（只接受一个对象）都会**抛出异常**

**组件名字全局唯一**，如果重复了，只会给容器中放一个最先声明的那个

默认情况下**组件在容器启动过程中就会创建**，每次获取相同组件都是同一个，即**组件是单例的**

## @Configuration

作用：标明配置类

**配置类**：用于分类管理组件的配置，配置类本身也是一个**组件**。

## SpringMVC分层注解

1、@Controller 控制器

2、@Service 服务层

3、@Repository 持久层

4、@Component 普通组件

三类层包括配置类都属于普通组件，内部无区别，为了提高代码分层可读性。

分层注解的组件必须在**主程序所在包及其子包结构下**。



@ComponentScan(basePackages = "包名") // 主程序下，组件按包名批量扫描（只扫描带分层注解的）

@Import(class字节码类) // 添加相应的第三方组件

## @Scope

调整组件的作用域（默认为”singleton“）

1、@Scope("prototype") 非单例

2、@Scope("singleton") 单例

3、@Scope("request") 同请求单例

4、@Scope("session") 同回话单例

**多实例不会在容器启动时创建，会在获取时创建。**

## @Lazy

**让单例组件不会在容器启动过程中创建**，会在获取时创建

## FactoryBean

创建对象比较复杂时，利用工厂方法进行创建

批量导入Bean组件

```java
@Component
public class FactoryBean1 implements FactoryBean<BeanName> {
    @Override
    public BeanName getObject() throws Exception {
        BeanName bean = new BeanName();
        return bean;
    }//创建组件
    @Override
    public Class<?> getObjectType() {return BeanName.class;}//组件类型
    @Override
    public boolean isSingleton() {return true;}//是否单例
}
```

## @Conditional

根据条件注册组件，修饰类或者方法

```java
public class Condition1 implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return false;
    }
}// 满足条件时创建组件

@Conditional(Condition1.class)
@Bean
...
```

| **@Conditional** **派生注解**      | **作用**                                                     |
| ---------------------------------- | ------------------------------------------------------------ |
| @ConditionalOnCloudPlatform        | 判定是否指定的云平台，支持：NONE、CLOUD_FOUNDRY、HEROKU、SAP、NOMAD、KUBERNETES、AZURE_APP_SERVICE |
| @ConditionalOnRepositoryType       | 判定是否指定的JPA类型，支持：AUTO、IMPERATIVE、NONE、REACTIVE |
| @ConditionalOnJava                 | 判断Java版本范围，支持：EQUAL_OR_NEWER、OLDER_THAN           |
| @ConditionalOnMissingBean          | 容器中没有指定组件，则判定true                               |
| @ConditionalOnMissingFilterBean    | 容器中没有指定的Filter组件，则判定true                       |
| @ConditionalOnGraphQlSchema        | 如果GraphQL开启，则判定true                                  |
| @ConditionalOnSingleCandidate      | 如果容器中指定组件只有一个，则判定true                       |
| @ConditionalOnClass                | 如果存在某个类，则判定true                                   |
| @ConditionalOnCheckpointRestore    | 判断是否导入了 org.crac.Resource ，导入则判定true            |
| @ConditionalOnNotWebApplication    | 如果不是Web应用，则判定true                                  |
| @ConditionalOnEnabledResourceChain | 如果web-jars存在或者resource.chain开启，则判定true           |

| **@Conditional** **派生注解**    | **作用**                                 |
| -------------------------------- | ---------------------------------------- |
| @Profile                         | 如果是指定Profile标识，则判定true；      |
| @ConditionalOnMissingClass       | 如果不存在某个类，则判定true             |
| @ConditionalOnWebApplication     | 如果是Web应用，则判定true                |
| @ConditionalOnResource           | 如果系统中存在某个资源文件，则判定true   |
| @ConditionalOnNotWarDeployment   | 如果不是war的部署方式，则判定true        |
| @ConditionalOnDefaultWebSecurity | 如果启用了默认的Security功能，则判断true |
| @ConditionalOnExpression         | 如果表达式计算结果为true，则判定true     |
| @ConditionalOnWarDeployment      | 如果是war的部署方式，则判定true          |
| @ConditionalOnBean               | 如果容器中有指定组件，则判定true         |
| @ConditionalOnThreading          | 如果指定的threading激活，则判定true      |
| @ConditionalOnProperty           | 如果存在指定属性，则判定true             |
| @ConditionalOnJndi               | 如果JNDI位置存在，则判定true             |

# 依赖注入

| **内容**        | **目标**                 |
| --------------- | ------------------------ |
| **@Autowired**  | 理解自动装配             |
| @Qualifier      | 理解类型/具名注入        |
| @Primary        | 理解多组件注入方式       |
| @Resource       | 扩展其他非Spring注解支持 |
| setter方法注入  | 理解setter方法注入       |
| **构造器注入**  | 理解构造器注入           |
| xxxAware        | 理解感知接口             |
| **@Value**      | 理解配置文件取值         |
| SpEL            | 理解Spring表达式基本使用 |
| @PropertySource | 理解 properties文件注入  |
| **@Profile**    | 理解多环境               |

```java
// 自动装配
// 1、按照类型匹配组件
// 2、类型找到多个，再匹配名称（新版）
@Autowired
UserService userService;// 自动装配，原理：Spring调用 容器.getBean
@Autowired
List<Person> personList;// 自动装配所有的Person到集合里
@Autowired
Map<String, Person> personMap;// 自动装配所有的(名称，Person)到Map里
@Autowired
ApplicationContext ac;// 获取ioc容器

@Qualifier("name")
@Autowired
...  // 注入指定名称的组件，对有默认组件注解的类可以用这个方法精准指定
    
@Primary
@Bean // 标记组件Bean为默认主组件，有多个相同类型的组件优先调用主组件
    // 标记之后无法使用更改组件名进行指定，可以用@Qualifier
```

@Resource 同是自动注入

区别是@Autowired只能Spring框架使用，@Resource更具有泛用性

@Autowired(required = false) 可以允许注入值为空，@Resource不具有这个功能



```java
// Spring 自动去容器中找构造器所需要的参数
public UserDao(Dog dog) {
    ...
}  // 有参构造器也可以实现自动注入Dog组件
```

**推荐有参构造器注入**



可以用set方法添加@Autowired注解，Spring对set、get方法中的参数会自动注入



 xxxAware  实现感知接口，获取需要的信息，通过set、get方法



自动注入只能注入组件，基本类型的属性值通过@Value进行注入

1、**@Value**("字面值")  直接复制

2、@Value("${... }")  动态从配置文件中取出某一项的值来赋值

@Value("${... : 默认值}")  ：后加默认值，如果找不到，就用默认值

// **application.properties** 为项目默认配置文件

// 用**@PropertySource("classpath:名称.properties")**  导入指定名称的配置文件

// classpath: 从自己项目类路径下找         classpath*: 从所有包类路径下找

3、@Value("#{**SpEL**}")  Spring Expression Language Spring表达式语言，用代码赋值



@Profile("环境标识")  当环境被激活时才会运行下面的类或者组件，默认环境为default

在项目配置文件中激活环境值，spring.profiles.active = default



无SpringBoot创建ioc：

```java
ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:ioc.xml");
// 所有Bean需要自己在xml文件中添加
```

# 生命周期

| **内容**          | **目标**                   |
| ----------------- | -------------------------- |
| @Bean             | 理解@Bean指定生命周期方法  |
| InitializingBean  | 理解Bean初始化             |
| DisposableBean    | 理解Bean销毁               |
| @PostConstruct    | 理解构造器**后置**处理钩子 |
| @PreDestroy       | 理解销毁预处理钩子         |
| BeanPostProcessor | 理解后置处理器机制         |



创建和运行：

![Spring1](C:\Users\25798\OneDrive\Notes\image\Spring1.png)

销毁：

![Spring2](C:\Users\25798\OneDrive\Notes\image\Spring2.png)



```java
@Bean(initMethod = "", destroyMethod = "") // 执行初始化时方法和销毁时方法
// 先执行构造器，再执行初始化

public class 类名 implements InitializingBean,DisposableBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化接口函数，在属性set之后执行
    }
    @Override
    public void destroy() throws Exception {
        // 销毁函数，在销毁时执行
    }
} // 接口实现方式

@PostConstruct // 构造器之后
public void 方法名() {
    ...
}
@PreDestroy // 销毁之前
public void 方法名() {
    ...
}

@Component // 拦截所有Bean的后置处理器，具有拦截修改功能
public class 类名 implements BeanPostProcessor {
    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        // 初始化之后执行
    }
    @Override
    public Object postProcessorBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 初始化之前执行
    }
}
```

# AOC(面向切面编程)

AOP：Aspect Oriented Programming（面向切面编程）

适用于**模块化的业务逻辑**

（例如**日志记录**、**事务管理**、**权限处理**、性能监控、**异常处理**、**缓存管理**、自动化测试、安全审计）

**依赖倒置**(设计模式)：依赖接口，而不是依赖实现

## 静态代理

**编码时**介入：包装真实对象，对外提供静态代理对象

1、包装被代理对象

2、实现被代理对象的接口

3、运行时调用被代理对象的真实方法

4、外部使用代理对象调用

优点：实现简单

缺点：需要为不同类型编写不同代理类，导致扩展维护性差

## 动态代理

运行时介入：创建真实对象运行时代理对象（拦截器）

实现步骤：

Java 反射提供 **Proxy.newProxyInstance** 的方式创建代理对象

优点：可以代理不同代理类

缺点：开发难度大，必须有接口，才能创建动态代理

## AOC

![Spring3](C:\Users\25798\OneDrive\Notes\image\Spring3.png)

**AOP实现步骤：**

1、导入 AOP 依赖

2、编写切面 Aspect

```java
@Component
@Aspect //声明是个切面
public class Aspect1 {
    /**
     * @Before：方法执行之前运行
     * @AfterReturning：方法执行正常返回结果运行
     * @AfterThrowing：方法抛出异常运行
     * @After：方法执行之后运行（类似finally代码块逻辑）
     * 切入点表达式：
     * execution(方法的全签名，或者省略名)
     * 省略方式：[public] void [com.cqupt.spring.aop.calculator].add(int, int)[throws Expecption]
     * 方法名用*表示所有方法（通配符写法）
     * ..表示有多个包层级或者多个参数（通配符写法）
     */
    @Before("execution(int *(int,int))")
    public void 方法名() {
        ...// 在所有符合条件的方法前切入执行
    }
}
```

切面可以切入底层方法，慎用通配符

3、编写通知方法

4、指定切入点表达式

5、测试 AOP 动态织入

## 切入表达式

| **类型**      | 语法                                                         | 解释&案例                                                    |
| ------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| **execution** | execution(modifiers-pattern?  ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)  throws-pattern?) | 最常用；匹配方法执行连接点  如：execution(*  com.example.service.*.*(..)) 匹配com.example.service包及其子包中所有类的所有方法。 |
| within        | within(type-pattern)                                         | 匹配指定类型内（包括子类型）的所有连接点  与execution表达式类似，但更侧重于类型而非具体的方法签名 |
| this          | this(type-pattern)                                           | 匹配代理对象是指定类型或其子类型的任何连接点                 |
| target        | target(type-pattern)                                         | 匹配目标对象是指定类型或其子类型的任何连接点  如：**target(com.example.MyBean)**   匹配所有目标对象是MyBean类型或其子类型的连接点。 |
| **args**      | args(param-pattern)                                          | 匹配方法**参数**是指定类型或其子类型的任何连接点  如：args(java.io.Serializable)；匹配所有参数是序列化接口的方法 |
| bean          | bean(bean-id-or-name-pattern)                                | 匹配特定Spring  bean的所有连接点。这依赖于Spring bean的名称或ID。 |

切入表达式判断注解：同样要写类名

| **类型**        | **语法**                     | 解释&案例                                                    |
| --------------- | ---------------------------- | ------------------------------------------------------------ |
| @target         | @target(annotation-type)     | 匹配标注了指定注解的所有目标对象的方法。  如@target(org.springframework.transaction.annotation.Transactional) |
| @args           | @args(annotation-type)       | 匹配方法**参数标注指定注解**。如：@args(com.atguigu.Hello)；  匹配参数上标注@Hello注解的 |
| @within         | @within(annotation-type)     | 匹配目标对象类型上拥有指定注解的所有方法。如：  @within(org.springframework.transaction.annotation.Transactional)  匹配所有目标对象类上被@Transactional注解标注 |
| **@annotation** | @annotation(annotation-type) | 匹配任何**被指定注解标注的方法**。如，@annotation(org.springframework.transaction.annotation.Transactional)  匹配所有被@Transactional注解标注的方法。 |
| 组合表达式      | &&、\|\|、!                  | &&代表同时成立、\|\|代表某个成立、!代表非(某个不成立)        |

**增强器链**：切面中的所有通知方法就是增强器。他们被组织成一个链路放到集合中，目标方法真正执行前后，会去增强器链中执行哪些需要提前执行的方法。

**AOP的底层原理**

1、Spring会为每个被切面切入的组件创建代理对象（Spring CGLIB创建的代理对象，无视接口）

2、代理对象中保存了切面类里面所有通知方法构成的增强器链。

3、目标方法执行时，会先去执行增强器链中拿到需要提前执行的通知方法去执行。

**AOP通知方法执行顺序：**

正常：前置通知 ==》目标方法 ==》返回通知 ==》后置通知

异常：前置通知 ==》目标方法 ==》异常通知 ==》后置通知

**JoinPoint连接点**

包装了当前目标方法的所有信息，即反射拿到的方法内容

```java
@Aspect

@Before("...")
public void 方法名(JoinPoint joinPoint) {
    //1、拿到方法全签名
    MethodSignature signature = (MethodSignature)joinPoint.getSignature();
    // 方法名
    String name = signature.getName();
    // 参数值
    Object[] args = joinPoint.getArgs();
}
@AfterReturning(value = "切入表达式", returning = "result")// result获取方法返回值
public void 方法名(JoinPoint joinPoint, Object result) {
    ...
}
@AfterThrowing(value = "切入表达式", throwing = "e")// 获取方法抛出的异常
public void 方法名(JoinPoint joinPoint, Exception e) {
    ...
}

@Pointcut("切入表达式")
public void pointCut(){}
@Before("pointCut()") // 自动调用注解下的切入表达式，简化代码
```

## 多切面执行顺序

切面一创建代理对象：

1、切面一前置

2、实际方法运行

3、切面一返回或异常

4、切面一后置

切面二创建代理对象：

1、切面二前置

2、实际方法运行：(1)切面一前置  (2)实际方法运行  (3)切面一返回或异常  (4)切面一后置

3、切面二返回或异常

4、切面二后置

**即多切面嵌套创建代理对象**，**切面嵌套顺序**：名称字母表靠前的在外层

可以通过注解 **@Order(1)** 来指定嵌套顺序，数字越小优先级越高，越先执行

# 常用工具类

TypeUtils、ReflectionUtils、AnnotationUtils、ClassUtils

# 容器底层原理

BeanFactory(组件工厂)，getBean先从getBeanFactory获取，工厂中存储组件详细信息beanDefinitionMap（创建组件时从中获取所有组件信息）

BeanFactory(组件工厂)的属性：

1、beanDefinitionMap 类型< Map > 存储组件详细信息

2、beanDefinitionNames 类型< List > 存储所有Bean的名字

3、singletonObjects < HashMap > 创建好的单例对象集合



bean获取过程中的形式：

NamedBeanHolder (bean包装类) 包含：beanName  beanInstance



底层获取bean方法：

getSingleton(beanName); // 获取单实例组件

**三级缓存**：

为了解决组件循环引用的问题，引入了三级缓存机制来创建和获取bean

```properties
# 允许循环引用,在项目配置文件中
spring.main.allow-circular-references=true
```

1、Map< String, Object > singletonObjects  单例对象池

存储创建好的单例对象

2、Map< String, Object > earlySingletonObjects  早期单例对象池

存储正在创建的单例对象，还未创建完成

3、Map< String, Object > singletonFactories  单例工厂池

存储使用工厂创建的单例对象( 实现了ObjectFactory接口的 )



getSingleton方法（**三级缓存机制**）：

a、查询1池，查询到就返回

b、查询2池，查询到就返回

c、加锁（synchronized）

d、查询1池，查询到就返回

e、查询2池，查询到就返回

f、查询3池，查询到就返回，并且将单例put进2池中，然后remove

g、无要查询的实例



创建单例过程：

a、将单例放入2池

b、运行构造器

c、@Autowired自动装配，getSingleton方法查询依赖的单例，查询不到就创建

d、将单例从2池拿出，放入1池

## 双检查锁

**三级缓存的机制**

懒汉式单例：

```java
public static SingletonLazy getInstance() {
    if(instance == null) {
        synchronized(SingletonLazy.class) {
            if(instance == null) {
                instance = new SingletonLazy();
            }
        }
    }
    return instance;
}
```

锁：并发下只有一个人进行

双检查：防止并发已经执行完代码，改变了检查语句的结果

## IOC容器刷新

Spring容器启动过程：

1、初始化Spring容器：实例化BeanFactory、实例化BeanDefinitionReader、实例化ClassPathBeanDefinitionScanner

2、注册内置BeanPostProcessor的BeanDefinition到容器中

3、配置类的BeanDefinition注册到容器中

4、调用refresh()方法刷新容器。**容器刷新12步**。

1）prepareRefresh()：进行一些刷新前的预处理工作

2）obtainFreshBeanFactory()：获取在容器初始化时创建的BeanFactory

3）prepareBeanFactory(beanFactory)：对BeanFactory进行预处理，向其中添加一些必要的组件。

4）postProcessBeanFactory(beanFactory)：接口，允许子类进行实现，对BeanFactory进一步处理。

5）invokeBeanFactoryPostProcessors(beanFactory)：执行BeanFactory的后置处理器，可以对BeanDefinition进行修改。

6）registerBeanPostProcessors(beanFactory)：注册Bean的后置处理器(获取所有后置处理器的名字)

7）initMessageSource(支持国际化消息)

8）initApplicationEventMulticaster：初始化应用事件多播器（事件驱动开发）同上获取该类型多播器的名字

9）onRefresh（预留子类接口）允许子类进行操作

10）registerListeners()：注册Spring的监听器

11） finishBeanFactoryInitialization(beanFactory)：创建所有非懒加载的单例

12）finishRefresh() ：完成容器启动

# 环绕通知

@Around （环绕通知）可以控制目标方法是否执行，修改目标方法参数、执行结果等

```java
// 固定写法：
// Object: 返回值
public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
    Object[] args = pjp.getArgs();// 获取目标方法的参数
    
    // 此处为前置通知位置
    try {
        Object proceed = pjp.proceed(args); 
    // 继续执行目标方法，类似于反射的method.invoke()
    // 可以修改方法参数args
        // 此处为返回通知位置
    } catch (Exception e) {
        // 此处为异常通知位置
        throw e; // 重点！！要抛出异常，不然外层异常通知无法运行
    } finally {
        // 此处为后置通知位置
    }
    
    return proceed;
}
```

## 执行顺序

环绕通知嵌套进目标方法中

1、前置通知( @Before )

2、环绕通知( @Around ) 的前置通知

3、目标方法执行

4、环绕通知( @Around ) 的返回或者异常通知

5、环绕通知( @Around ) 的后置通知

6、返回或者异常通知( @AfterReturning || @AfterThrowing )

7、后置通知( @After )

**注意：要在环绕通知中抛出异常，外层的异常通知才会生效**

# 事务

1、获取数据库连接

2、设置非自动提交

**3、执行SQL**

**4、封装返回值**

5、正常：提交 || 异常：回滚

6、关闭链接

## 声明式事务

•编程式：通过编写业务代码，程序员自行完成指定功能

•声明式：通过声明业务需求，框架自动完成指定功能

声明式定义：只需要告诉框架，这个方法需要事务，框架会自动在运行方法时执行事务的流程控制逻辑，不需要自己编程。

优点：代码量小，高效

缺点：封装程度高，不易于排错

**项目配置连接MySQL**

导入包：spring-boot-starter-data-jdbc 和 mysql-connector-java

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/包名
spring.datasource.username=root
spring.datasource.password=123456
spring.datasouce.driver-class-name=com.mysql.cj.jdbc.Driver
```

**连接Mysql**

```java
@Autowired
DataSource dataSource;
Connection connection = dataSource.getConnection();

@Autowired
JdbcTemplate jdbcTemplate; // 就是QueryRunner,上面可以不需要
// 可以执行CRUD操作，可以执行SQL语句
```

**默认连接为 HikariProxyConnection** （DruidDataSource 速度慢，防SQL注入）

```java
// 按照id查询图书类：
public Book getBookById(Integer id) {
    String sql = "select * from book where id = ?";
    Book book = jdbcTemplate.queryForObject(sql, 
        new BeanPropertyRowMapper<>(Book.class), id);}
// 添加图书
public void addBook(Book book) {
    String sql = "insert into book(bookName,price,stock) values(?,?,?)";
    jdbcTemplate.update(sql,book.getBookName(),book.getPrice(),
                        book.getStock());
}
// 按照id减少库存
public void updateBookStock(Integer bookId, Integer num) {
    String sql = "update book set stock=stock-? where id=?";
    jdbcTemplate.update(sql,num,bookId);
}
// 按照id删除图书
public void deleteBook(Integer id) {
    String sql = "delete from book where id=?";
    jdbcTemplate.update(sql,id);
}
```

注解开启事务：

```java
@EnableTransactionManagement // 开启基于注解的事务管理，主程序中添加

@Transactional
... // 事务方法
```

## 底层原理

**底层**有一个事务管理器transactionManager，默认使用JdbcTransactionManager，提供事务的提交和回滚方法

原理：有一个事务拦截器TransactionInterceptor，是一个AOP切面，控制事务管理器中提交和回滚的方法调用。通过反射获取是否有异常出现，异常回滚、不异常提交。

## 事务管理器

事务管理器中timeout(同timeoutString)超时时间，秒为单位（int），超时后事务就会回滚。**超时只计算到最后一条数据库操作的时间，不包括后续语句执行时间。**

readOnly：数据只需要读操作，标注只读true可以优化性能。

rollbackFor：额外指明哪些异常回滚。

```java
(rollbackFor = {Exception.class})
```

不是所有异常都回滚，默认**只在运行时异常回滚**。

**异常分为：**

**1、运行时异常**（unchecked exception [非受检异常]）

**2、编译时异常**（checked exception [受检异常]）

noRollbackFor：指明哪些异常不需要回滚

isolation：隔离级别

propagation：传播行为

### 隔离级别

1、读未提交（Read Uncommitted）

•事务可以读取未被提交的数据，易产生脏读、不可重复读和幻读等问题

2、读已提交（Read Committed）

•事务只能读取已经提交的数据，可避免脏读，但可能引发不可重复读和幻读。

3、可重复读（Repeatable Read）(快照读)**（MySQL默认）**

•同一事务期间多次重复读取的数据相同。避免脏读和不可重复读，但仍有幻读的问题

4、串行化（Serializable）

•最高隔离级别，完全禁止了并发，只允许一个事务执行完毕之后才能执行另一个事务

| 级别 \ 问题 | **脏读** | **不可重复读** | **幻读** |
| ----------- | -------- | -------------- | -------- |
| 读未提交    | √        | √              | √        |
| 读已提交    | ×        | √              | √        |
| 可重复读    | ×        | ×              | √        |
| 串行化      | ×        | ×              | ×        |

### 传播行为

控制事务的嵌套情况，小事务要不要和大事务进行绑定。

| **传播行为**     | **产生效果**                                                 |
| ---------------- | ------------------------------------------------------------ |
| **REQUIRED**     | 支持当前事务，如果不存在则创建一个新的事务                   |
| SUPPORTS         | 支持当前事务，如果不存在则非事务性执行                       |
| MANDATORY        | 支持当前事务，如果不存在则抛出异常                           |
| **REQUIRES_NEW** | 创建一个新事务，并在存在当前事务时挂起当前事务               |
| NESTED           | 如果当前存在事务，则在嵌套事务中执行，否则像  REQUIRED 一样运行 |
| NOT_SUPPORTED    | 非事务执行，如果存在当前事务则暂停                           |
| NEVER            | 非事务性地执行，如果存在事务则抛出异常                       |

**NESTED**类似存档点，回滚到未抛异常处的**保存点**

**外层事务回滚，不会影响内层事务；但是内层事务回滚会影响外层事务。(异常机制决定)**

**并且考虑回滚（异常）后面的代码都不执行。**

**事务的参数设置项也会一起向下传播**
