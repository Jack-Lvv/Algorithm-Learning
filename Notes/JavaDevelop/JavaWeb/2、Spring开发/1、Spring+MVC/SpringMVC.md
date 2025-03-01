[TOC]

# SpringMVC

官网：https://docs.spring.io/spring-framework/reference/web/webmvc.html#mvc

•SpringMVC 是 **Spring 的 web 模块**，用来**开发Web应用**

•SprinMVC 应用最终作为 B/S、C/S 模式下的 **Server 端**

•**Web应用的核心**就是 **处理HTTP请求响应**

底层基于Servlet API（web开发不一定必须要用Servlet，例如Netty框架），都是基于http协议

**导入模块spring-webmvc**

前后端不分离：业务处理完的数据直接在服务端渲染成页面，再传输页面到浏览器。

前后端分离：服务器业务处理完成，将数据**序列化**成字符串传输给前端，前端**反序列化**获得数据后渲染页面。

SpringMVC两者都支持。

优点(SpringBoot 的效果)：

1、自动整合Tomcat

2、Servlet开发大大简化，不用实现任何接口

3、自动解决了中文乱码等问题

端口修改，在项目配置文件中：

```properties
server.port=8888
```

# @RequestMapping

## 路径映射

组件默认为单例，调用一次组件路径，即运行一次组件中的方法。

路径规则：https://docs.spring.io/spring-framework/docs/6.1.11/javadoc-api/org/springframework/web/util/pattern/PathPattern.html

```java
@ResponseBody  // 方法仅作为响应体，不跳转页面
@RequestMapping("/hello")  // 路径映射，默认为跳转页面

@RestController // 同上，整个类都为响应体路径映射（前后端分离开发用这个）
```

路径处允许通配符。

*：匹配任意多个字符（**包括0个**）

**：匹配任意多层路径（只能单独占一层路径）

？：匹配任意单个字符

如果通配符路径匹配到多个路径，则**精确优先**，即没有通配符的优先。

**精确度：完全匹配 > ? > * > ****

精确路径全局唯一，不能重复。

## 请求限定

1、请求方式：method

2、请求参数：params

3、请求头：headers

4、请求内容类型：consumes

5、响应内容类型：produces

```java
@RequestMapping(value = "/test", method = RequestMethod.POST)
// 限定请求方法为POST
```

**请求方式**包括：

GET、POST、HEAD、PUT、PATCH、DELETE、OPTIONS、TRACE

```JAVA
@RequestMapping(value = "/test", params = {"age = 18"})
// 限定请求参数必须包括age = 18
```

**GET请求格式：URL?参数1&参数2**

```java
@RequestMapping(value = "/test", headers = "head")
// 限定请求必须包括请求头“head”
```

```java
@RequestMapping(value = "/test", consumes = "application/json")
// 限定请求内容必须是json格式
// 限定MediaType （表单、json、图片、视频音频流、markdown）
```

```java
@RequestMapping(value = "/test", produces = "application/json")
// 限定响应数据类型
```

# HTTP

请求首行：请求方式、请求路径、请求协议

POST /test HTTP/1.1

请求头：k: v  \n k: v \n

请求体：此次请求携带的其他数据（POST请求数据）

URL 携带大量数据，特别是GET请求，会把参数放在URL上

**URL地址格式：**

![SpringMVC1](C:\Users\25798\OneDrive\Notes\image\SpringMVC1.png)

http协议端口默认80，https协议端口默认443

#片段用于前端定位页面，不会发给后端

大量数据，文件传输，安全需求用POST

•请求头 有很多重要信息，SpringMVC 可以快速获取到

•请求体 携带大量数据，特别是POST请求，会把参数放在请求体中

# JSON数据格式

JavaScript Object Notation(JavaScript 对象表示法)

•  JSON用于将结构化数据表示为 JavaScript 对象的标准格式，通常用于在网站上表示和传输数据

•  JSON 可以作为一个对象或者字符串存在

1、前者用于解读 JSON 中的数据，后者用于通过网络传输 JSON 数据。

2、JavaScript 提供一个全局的 可访问的 JSON 对象来对这两种数据进行转换。

•  **JSON 是一种纯数据格式，它只包含属性，没有方法。**

将字符串转换为原生对象称为反序列化（deserialization），

而将原生对象转换为可以通过网络传输的字符串称为序列化（serialization）。

```json
{
    "key":"value",
    "array":[
        {
            "key1":"value1",
            "key2":"value2"
        }
    ]
}
```

# 请求处理

| **内容**                                                     | **目标**                |
| ------------------------------------------------------------ | ----------------------- |
| 使用普通变量，收集请求参数(直接用方法接收，要求参数名和变量名相同)(POST和GET都可以) | 普通value封装           |
| 使用@RequestParam，逐一封装多个参数(参数名和变量名不同，也可以将参数赋值给变量)  默认标注后一定要有 | @RequestParam与required |
| **使用POJO，统一封装多个参数**                               | **bean封装**            |
| 使用@RequestHeader获取请求头数据                             | @RequestHeader          |
| 使用@CookieValue获取Cookie数据                               | @CookieValue            |
| 使用POJO，级联封装复杂对象                                   | 级联封装                |
| **使用@RequestBody，封装JSON对象**                           | **JSON封装**            |
| 使用@RequestPart/@RequestParam，封装文件对象                 | 文件上传                |
| 使用HttpEntity，封装请求原始数据                             | HttpEntity              |
| 使用原生Servlet  API，获取原生请求对象                       | Servlet  API            |

```java
@RequestMapping("/test")
public String handle(@RequesetParam(value = "username", required = false) String name) {
    ... // @RequestParam将参数赋予name，required表示是否必须有这个参数传入
}
```

如果目标方法参数是一个pojo(类对象)，SpringMVC会自动把请求参数和pojo属性进行匹配，要求**传入参数名要与类属性名相同**。

```java
@RequestMapping("/test")
public String handle(@RequesetHeader(value = "host", defaultValue = "127.0.0.1") String host) {
    ... // @RequestHeader 获取请求的请求头host
}
```

```java
@RequestMapping("/test")
public String handle(@CookieValue("test") String test) {
    ... // @CookieValue获取请求中cookie的值
}
```

前后端分离不需要用cookie。

```java
@RequestMapping("/test")
public String handle(@RequesetBody Person person) {
    ... // @RequestBody 获取json数据字符串，反序列化转为person对象
}
```

```java
@RequestMapping("/test")
public String handle(@RequesetParam("文件参数名") MultipartFile file) {
    ... // 用MultipartFile接收文件数据
        file.transferTo(new File("路径" + originalFilename));
    // 保存文件到路径
} 
```

配置文件：

```properties
spring.servlet.multipart.max-file-size=1GB
spring.servlet.multipart.max-request-size=10GB
```

单次最大文件上传大小、单次请求文件最大大小

HttpEntity<> entity ：获取全部请求内容，包括请求头和请求体，请求体可以转pojo

接受原生Servlet API：HttpServletRequest、HttpServletResponse

**请求参数类型：**

| **参数**                            | **作用**                                  |
| ----------------------------------- | ----------------------------------------- |
| WebRequest, NativeWebRequest        | 非Servlet  API 下使用的request            |
| **ServletRequest, ServletResponse** | **Servlet API 下使用的request、response** |
| HttpSession                         | session对象                               |
| PushBuilder                         | HTTP/2 数据推送组件                       |
| Principal                           | 当前认证的用户                            |
| HttpMethod                          | 请求方式                                  |
| Locale                              | 区域信息                                  |
| TimeZone + ZoneId                   | 区域信息                                  |
| InputStream、Reader                 | 请求体数据流                              |
| OutputStream、Writer                | 响应体数据流                              |
| **@PathVariable**                   | **URL 路径变量**                          |

| **参数**                  | **作用**              |
| ------------------------- | --------------------- |
| @MatrixVariable           | 矩阵变量              |
| **@RequestParam**         | **请求参数**          |
| @RequestHeader            | 请求头                |
| @CookieValue              | Cookie值              |
| **@RequestBody**          | **请求体**            |
| HttpEntity< B >           | 请求头+请求体         |
| @RequestPart              | 文件项                |
| Map、Model、ModelMap      | 服务端渲染共享数据    |
| @ModelAttribute（MA）     | 前置数据绑定          |
| **Errors, BindingResult** | **数据校验结果**      |
| @SessionAttributes        | session数据           |
| UriComponentsBuilder      | 封装请求URL           |
| @RequestAttribute         | 请求域中属性          |
| 其他任何参数              | 当做@RequestParam或MA |

# 响应处理

| **内容**                                        | **目标**         |
| ----------------------------------------------- | ---------------- |
| **返回JSON数据(返回对象或者Map会自动转为json)** | **对象json写出** |
| **返回ResponseEntity**                          | **文件下载**     |
| （了解）：引入thymeleaf模板引擎                 | 服务端渲染       |

SpringMVC 底层使用 **HttpMessageConverter** 处理json数据的序列化与反序列化

**ResponseEntity：**

1、**Content-Disposition** 响应头 指定文件名信息，文件名如果有中文还需要 URLEncoder 进行编码

2、**ContentType** 响应头 指定响应内容类型，是一个 OCTET_STREAM（8位字节流）

3、**ContentLength** 响应头 指定内容大小

4、body 指定具体响应内容（文件字节流）；也可以用 **InputStreamResource** 替换 **byte[]**，防止oom(内存溢出)

```java
// 文件下载模版
@RequestMapping("/download")
public ResponseEntity<InputStreamResource> download() throws IOException {
    FileInputStream inputStream = new FileInputStream("文件路径");
    // byte[] bytes = inputStream.readAllBytes();文件过大会内存溢出
    InputStreamResource resource = 
        new InputStreamResource(inputStream);// 使用流传输不会溢出
    String encode = URLEncoder.encode("文件名", "UTF-8");// 解决中文乱码
    return ResponseEntity.ok()
        // 内容类型，流
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        // 内容大小
        .contentLength(inputStream.available())
        // Content-Disposition 内容处理方式
        .header("Content-Disposition", "attachment;filename="+encode)
        .body(resource);
}
```

**响应数据类型**：

| **类型**                                 | **作用**                             |
| ---------------------------------------- | ------------------------------------ |
| **@ResponseBody + 对象**                 | **响应json等非页面数据**             |
| **HttpEntity< B >, ResponseEntity< B >** | **响应完全自定义响应头、响应体数据** |
| HttpHeaders                              | 仅返回响应头，无内容                 |
| ErrorResponse                            | 响应错误头、错误体                   |
| ProblemDetail                            | 响应错误体                           |
| **String**                               | **逻辑视图地址**                     |
| View                                     | 视图对象                             |
| Map、Model                               | 默认视图地址与数据                   |
| @ModelAttribute                          | 默认视图地址与数据                   |
| ModelAndView                             | 自定义模型和视图                     |
| void                                     | 需要自定义response                   |
| DeferredResult                           | 异步结果响应                         |
| Callable                                 | 异步结果响应                         |

| **类型**（几乎没用）                                         | **作用**               |
| ------------------------------------------------------------ | ---------------------- |
| ListenableFuture< V >,  CompletionStage< V >, CompletableFuture< V > | 异步结果响应           |
| ResponseBodyEmitter,   SseEmitter                            | 异步响应与流式数据响应 |
| StreamingResponseBody                                        | 流式数据响应           |
| ReactiveAdapterRegistry                                      | Webflux 模式           |
| 其他返回                                                     | 被当做ModelAttribute   |

# RESTful

REST（Representational State Transfer 表现层状态转移）是一种软件架构风格；

官网：https://restfulapi.net/

使用资源名作为URL，使用HTTP的请求方式表示对资源的操作

以 员工的 增删改查 为例，设计的 **RESTful API** 如下

| URI             | 请求方式 | 请求体        | 作用         | 返回数据              |
| --------------- | -------- | ------------- | ------------ | --------------------- |
| /employee/{id}  | GET      | 无            | 查询某个员工 | Employee JSON         |
| /employee       | POST     | employee json | 新增某个员工 | 成功或失败状态        |
| /employee       | PUT      | employee json | 修改某个员工 | 成功或失败状态        |
| /employee/{id}  | DELETE   | 无            | 删除某个员工 | 成功或失败状态        |
| /employees      | GET      | 无/查询条件   | 查询所有员工 | List< Employee > JSON |
| /employees/page | GET      | 无/分页条件   | 查询所有员工 | 分页数据  JSON        |

调用第三方方法：

1、通过API发送请求，获取响应数据

2、SDK：导入jar包

```java
@GetMapping("/employee/{id}")
// 同@RequestMapping(value = "/employee/{id}", method = Request.GET)
public String get(@PathVariable Integer id) {
    ... // 通过@PathVariable获取路径中的变量
}
```

@XxxMapping：REST风格的简化写法

后端返回数据给前端使用统一json格式：

```json
{
    "code":"200",
    "msg":"ok",
    "data":"返回的数据"
}
```

**common包中的R来定义统一的json格式**

# 跨域

CORS policy：同源策略

跨源资源共享（CORS）(Cross-Origin Resource Sharing)

浏览器请求要去的服务器和当前项目所在服务器必须是同一个源(服务器)

仅限（ajax请求、图片、css、js等）

1、可以通过前端解决

2、后端：设置允许前端跨域

服务器给浏览器的响应头中添加字段：Access-Control-Allow-Origin = *

即注解**@CrossOrigin** -- 允许跨域

浏览器先**发送一条OPTIONS请求查看服务器是否允许跨域**（预检请求），允许后再发送第二条真正请求（只有复杂的请求会预检，简单请求不会GET、POST）

# 路径变量

/resources/{name}：**最多使用**

  •  {} 中的值封装到 name 变量中

/resources/{*path}：

  •  {} 中的值封装到 path 变量中

  •  /resources/image.png： path = /image.png

  •  /resources/css/spring.css：path = /css/spring.css

/resources/{filename:\\w+}.dat：

  •  {} 中的值封装到 filename 变量中; filename 满足 \\w+ 正则要求

  •  /resources/{filename:\\w+}.dat

  •  /resources/xxx.dat：xxx是一个或多个字母

# 拦截器

**SpringMVC 内置拦截器机制** ，允许在请求被目标方法处理的前后进行拦截，执行一些**额外操作**；比如：**权限验证、日志记录、数据共享**等...



**使用步骤**

1、实现 HandlerInterceptor 接口的组件类即可成为拦截器

2、创建 **WebMvcConfigurer** 组件，并在配置类中配置拦截器的拦截路径

```java
@Configuration
public class MySpringMVCConfig implements WebMvcConfigurer {
    @Autowired
    MyHandlerInterceptor myHandlerInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myHandlerInterceptor)
            .addPathPatterns("/拦截的URL路径");
    }
}
@Component
public class MyHandlerInterceptor {
    // 拦截方法重写
}
```

3、重写下面三个方法进行拦截，**preHandle方法返回true即放行**

4、执行顺序：preHandle => 目标方法 => postHandle => afterCompletion



**多拦截器执行顺序：**

顺序preHandle => 目标方法 => 倒序postHandle => 渲染 => 倒序afterCompletion

**• 只有执行成功的 preHandle 会倒序执行 afterCompletion**

**• postHandle 、afterCompletion 从哪里炸，倒序链路从哪里结束**

**• postHandle 失败不会影响 afterCompletion 执行**

![SpringMVC2](C:\Users\25798\OneDrive\Notes\image\SpringMVC2.png)

|          | **拦截器**                        | **过滤器**                                                   |
| -------- | --------------------------------- | ------------------------------------------------------------ |
| 接口     | HandlerInterceptor                | Filter                                                       |
| 定义     | Spring 框架                       | Servlet 规范                                                 |
| 放行     | preHandle 返回 true 放行请求      | chain.doFilter() 放行请求                                    |
| 整合性   | 可以直接整合Spring容器的所有组件  | 不受Spring容器管理，无法直接使用容器中组件  需要把它放在容器中，才可以继续使用 |
| 拦截范围 | 拦截  SpringMVC 能处理的请求      | 拦截Web应用所有请求                                          |
| 总结     | SpringMVC的应用中，推荐使用拦截器 | 非Spring应用用过滤器                                         |

可以使用原生Filter，但是注解失效

# 异常处理

**编程式异常处理：**不适用于大量的异常处理

•try - catch、throw、exception

**声明式异常处理：**

SpringMVC 声明式注解来进行快速的异常处理

•@ExceptionHandler：可以处理指定类型异常，仅能处理本类的异常

•@ControllerAdvice：可以集中处理所有Controller的异常，类似切面

•@ExceptionHandler + @ControllerAdvice： 可以完成全局统一异常处理

```java
@ExceptionHandler(ArlithmeticException.class)
// 声明下面的方法处理异常的类型
public R handleArithmeticException() {
    return R.error(100, "执行异常");
}
@ExceptionHandler(Throwable.class)
// 处理其他所有异常
```

**异常处理优先级：**

1、本类 > 全局

2、精确 > 模糊

出现了异常，并没有对应的异常处理，会调用**SpringBoot的默认异常处理机制**。

自适应的异常处理：

1、浏览器发的请求，出现异常返回默认错误页面

2、移动端发的请求，出现异常返回默认json错误数据；项目开发的时候错误模型需要按照项目的标准走

**项目规范：要编写异常处理覆盖所有情况，不使用默认异常处理**

新建枚举类和异常处理类

```java
public enum BizExceptionEnume {
    ORDER_CLOSED(10001, "订单已关闭"),
    ORDER_NOT_EXIST(10002,"订单不存在");
    @Getter
    private Integer code;
    @Getter
    private String msg;
    private BizExceptionEnume(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
```

1、自定义业务异常类：BizException

2、自定义异常枚举类：BizExceptionEnume

3、后端只需编写异常逻辑，出错只需抛出异常throw

4、自定义全局异常处理器：GlobalExceptionHandler，处理异常，返回给前端json数据

抛出自定义枚举类异常，自定义异常类处理，全局异常处理器拦截返回上层异常

# 数据校验

要进行双端校验，前后端都需要数据校验。

JSR 303 是 Java 为 **Bean** 数据合法性校验 提供的标准框架，通过在 **Bean** **属性上** 标注 类似于 @NotNull、@Max 等标准的注解指定校验规则，并通过标准的验证接口对Bean进行验证。

1、引入校验依赖：spring-boot-starter-validation

2、定义封装数据的Bean

3、给Bean的字段标注校验注解，并指定校验错误消息提示

4、在方法类名使用@Valid、@Validated开启校验

5、检验对象名后使用 BindingResult 获取封装校验结果

6、结合全局异常处理，统一处理数据校验错误，获取MethodArgumentNotValidException.class异常进行处理



1、使用自定义校验注解 + 校验器(implements ConstraintValidator) 完成字段自定义校验规则

2、结合校验注解 message属性 与 i18n 文件，实现错误消息国际化，{message}占位符，在项目配置文件中新建message进行配置

| **校验注解**                | **作用**                                                     |
| --------------------------- | ------------------------------------------------------------ |
| @AssertFalse                | 验证Boolean类型字段是否为false                               |
| @AssertTrue                 | 验证Boolean类型字段是否为true                                |
| @DecimalMax                 | 验证字符串表示的数字是否小于等于指定的最大值                 |
| @DecimalMin                 | 验证字符串表示的数字是否大于等于指定的最小值                 |
| @Digits(integer, fraction)  | 验证数值是否符合指定的格式，integer指定整数精度，fraction指定小数精度 |
| @Email                      | 验证字符串是否为邮箱地址格式                                 |
| @Future                     | 验证日期是否在当前时间之后                                   |
| @Past                       | 验证日期是否在当前时间之前                                   |
| **@Min(value)**             | **验证数字是否大于等于指定的最小值**                         |
| **@Max(value)**             | **验证数字是否小于等于指定的最大值**                         |
| @Null                       | 验证对象是否为null                                           |
| **@NotNull**                | **验证对象是否不为null,  与@Null相反（a!=null）**            |
| **@NotEmpty**               | **验证字符串是否非空（a!=null  && a!=“”）**                  |
| **@NotBlank**               | **验证字符串是否非空白字符（a!=null  &&a.trim().length  > 0）** |
| **@Size(max=, min=)**       | **验证字符串、集合、Map、数组的大小是否在指定范围内**        |
| **@Pattern(regex=, flag=)** | **验证字符串是否符合指定的正则表达式**                       |

**JavaBean分层：**

Pojo：普通java类

Dao：数据访问对象，访问数据库对象

VO：值对象、视图对象 用于封装前端数据的对象(数据脱敏操作等)

TO：传输数据对象

```java
// vo转为Dao使用BeanUtils.copyProperties(base, target)
Employee employee = new Employee();
BeanUtils.copyProperties(vo, employee);
```

# Swagger

1、写入依赖

2、写入项目配置

3、标注注解

4、访问http://ip:port/doc.html 即可查看接口文档

| **注解**     | **标注位置**        | **作用**               |
| ------------ | ------------------- | ---------------------- |
| @Tag         | controller 类       | 描述  controller 作用  |
| @Parameter   | 参数                | 标识参数作用           |
| @Parameters  | 参数                | 参数多重说明           |
| @Schema      | model 层的 JavaBean | 描述模型作用及每个属性 |
| @Operation   | 方法                | 描述方法作用           |
| @ApiResponse | 方法                | 描述响应状态码等       |

# 日期

数据库类型：

date 日期  time 时间  datetime 日期时间

java中为：date

前端反序列化格式：

```java
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
private Date birth;// 设置日期格式
```

# 底层源码

DispatcherServlet收集请求，反射调用方法。

基于原生HttpServlet

**DispatcherServlet九大组件**

**1、MultipartResolver**（文件传输解析器）

2、LocaleResolver（国际化本地信息解析器）

3、ThemeResolver（主题解析器）现前端替代此功能，已过时

**4、List< HandlerMapping >**（处理器映射）

**5、List< HandlerAdapter >**（处理器适配器）反射执行方法工具

**6、List< HandlerExceptionResolver >**（处理器异常解析器）

7、RequestToViewNameTranslator（请求视图名翻译器）同3

8、FlashMapManager（闪存管理器）同3

9、List< ViewResolver >（视图解析器）同3

![SpringMVC3](C:\Users\25798\OneDrive\Notes\image\SpringMVC3.png)

**HttpMessageConverter**：将响应的对象转换为json

**HandlerMapping** ==> HashMap<请求路径, 处理器>，底层保存了每个请求由哪个handler处理的映射关系

**HandlerExecutionChain** 从handlerMapping中找到处理器执行链： **目标方法 + 所有拦截器**

**RequestMappingHandlerAdapter**：专门反射**执行那些标注了@RequestMapping注解的方法**

![SpringMVC4](C:\Users\25798\OneDrive\Notes\image\SpringMVC4.svg)
