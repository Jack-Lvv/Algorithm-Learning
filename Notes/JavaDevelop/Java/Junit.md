[TOC]

# 测试范围

分支覆盖率、变异杀死率、运行效率、可读性

版本：JDK 8、JUnit 4.12

最终提交单个 .java 文件

## 算法逻辑

检查算法及其内部处理逻辑的正确性

## 模块接口

形参个数、类型、次序、返回值类型

调用部分、实参的个数、类型、次序、返回值

多态着重检查

## 数据结构

检查全局和局部数据结构的定义  实现和使用

## 边界条件

需求的边界、变量类型本身的边界

## 独立路径

遗漏和错误的处理逻辑导致缺失独立路径

## 异常处理

未提供有效的异常处理，或处理不正确

## 输入数据

正确性、规范性、合理性进行检查

# 注解

@ Test       测试方法注解

@ Test （timeout = 2000） 限制测试时间

@ Before 在单个测试方法前运行，创建对象

@ After 在单个测试方法后运行，释放对象和空间

# 覆盖

语句覆盖

**分支覆盖**：判定语句多个分支都要覆盖

条件覆盖：判定条件真假都要覆盖

判定覆盖：每个真假取值要独立组合判定覆盖



为文件中代码所有的类编写Junit4单元测试(仅使用junit4工具进行测试，不使用mockito，且仅使用文件代码中的方法，不存在文件中没有的其他方法)，仅关心分支覆盖率(包括判断语句的多个分支)和pit标准变异的杀死率，越高越好。所有测试方法写在一个Test类中。变异类别有替换自增自减符号、移除空返回类型的方法调用、修改返回值、修改数学运算符、翻转条件判断语句或者边界值、反转负数。注意不能出现编译和运行报错。下面将给你多个文件，可能需要多次上传，等我上传完之后告诉你。

# 变异

将自增/自减运算符（例如 `i++` 或 `i--`）替换为其他形式

移除对 `void` 类型方法的调用

修改返回值，如将 `true` 替换为 `false` 或返回 `null`

更改数学运算符（如 `+` 改为 `-`）

反转条件语句（如将 `if (a > b)` 变为 `if (a <= b)`）

反转负数，使其变为正数

调整条件边界值，如将 `>=` 改为 `>`



# 断言

```java
assertTrue([msg], bool); //bool为真

assertfalse([msg], bool);//bool为假

assertEquals([msg], expected, actual);//数值判断

assertNull([msg], obj); //空值判断

assertNotNull([msg], obj);//非空值判断

assertSame([msg], expected, actual);// 对象相同

assertNotSame([msg], expected, actual);// 对象不同
```

# 控制台输出测试

对输出内容截取System.out来检测

```java
ByteArrayOutputStream outContent = new ByteArrayOutputStream();
PrintStream originalOut = System.out;
System.setOut(new PrintStream(outContent));
// 执行位置
assertEquals("Hello, JUnit!\n", outContent.toString());
System.setOut(originalOut);


public static final String sep = System.getProperty("line.separator");
// 换行符


```

# 异常断言

```java
@Rule
public ExpectedException thrown = ExpectedException.none();

@Test
try {
    ...
} catch (ArithmeticException e) {
     // 进一步验证异常消息
     assertEquals("Division by zero is not allowed", e.getMessage());
}
```



执行命令：

mvn clean test-compile org.pitest:pitest-maven:mutationCoverage

