[TOC]

# 集合体系结构

Collection代表单列集合，每个元素（数据）只包含一个值。

Map代表双列集合，每个元素包含两个值（键值对）。

**Collection**集合

​	**List系列集合**：添加的元素是有序、可重复、有索引。

   	 ArrayList、LinekdList ：有序、可重复、有索引。

​	**Set系列集合**：添加的元素是无序、不重复、无索引。

   	 HashSet: 无序、不重复、无索引；
   	
   	  LinkedHashSet: 有序、不重复、无索引。
   	
   	 TreeSet：按照大小默认升序排序、不重复、无索引。

**Map**集合：键值对集合

所有键是不允许重复的，但值可以重复，键和值是一 一对应的，每一个键只能找到自己对应的值。

Map系列集合的特点都是由**键决定**的，值只是一个附属品，值是不做要求的

​	HashMap: 无序、不重复、无索引； （用的最多）

​	LinkedHashMap :由键决定的特点：有序、不重复、无索引。

​	TreeMap :按照大小默认升序排序**、**不重复、无索引。

# ===========

# String

String代表字符串，它的对象可以封装字符串数据，并提供了很多方法完成对字符串的处理。

## 构造器

```java
String str = new String();
```

| 构造器                         | 说明                                   |
| ------------------------------ | -------------------------------------- |
| public String()                | 创建一个空白字符串对象，不含有任何内容 |
| public String(String original) | 根据传入的字符串内容，来创建字符串对象 |
| public String(char[] chars)    | 根据字符数组的内容，来创建字符串对象   |
| public String(byte[] bytes)    | 根据字节数组的内容，来创建字符串对象   |

## 方法

| 方法名                                                       | 说明                                                     |
| ------------------------------------------------------------ | -------------------------------------------------------- |
| public int length()                                          | 获取字符串的长度返回（就是字符个数）                     |
| public char charAt(int index)                                | 获取某个索引位置处的字符返回                             |
| public char[] toCharArray()：                                | 将当前字符串转换成字符数组返回                           |
| public boolean equals(Object anObject)                       | 判断当前字符串与另一个字符串的内容一样，一样返回true     |
| public boolean equalsIgnoreCase(String anotherString)        | 判断当前字符串与另一个字符串的内容是否一样(忽略大小写)   |
| public String substring(int beginIndex, int endIndex)        | 根据开始和结束索引进行截取，得到新的字符串（包前不包后） |
| public String substring(int beginIndex)                      | 从传入的索引处截取，截取到末尾，得到新的字符串返回       |
| public String replace(CharSequence target, CharSequence replacement) | 使用新值，将字符串中的旧值替换，得到新的字符串           |
| boolean contains(CharSequence s)                             | 判断字符串中是否包含了某个字符串                         |
| boolean startsWith(String prefix)                            | 判断字符串是否以某个字符串内容开头，开头返回true，反之   |
| String[] split(String regex)                                 | 把字符串按照某个字符串内容分割，并返回字符串数组回来     |
| int indexOf(CharSequence s)                                  | 字符串中包含某个字符串第一个匹配项的下标                 |
| int compareTo(String str)                                    | 字符串字典序的比较器                                     |
| String[] split(String str)                                   | 以str为表示进行分割                                      |

String 进行 == 判等判断的是地址，不能比较字符串内容

new的String地址不同，常量赋值的String地址相同

# StringBuilder

**StringBuilder**代表可变字符串对象，相当于是一个**容器**，它里面装的字符串是可以改变的，就是用来操作字符串的。

好处：StringBuilder比String更适合做字符串的**修改操作**，效率会更高，代码也会更简洁。

1、**Spring** 是不可变字符串，每对Spring进行造作后都会产生一个新的字符串对象。
2、**SpringBuffer** 可变字符串，可以对字符串进行添加等操作，那么我们可变不可变都有了为什么还要有一个SpringBuilder，因为SpringBuffer是线程安全的，支持多并发，所以我们在多线程中使用SpringBuffer。
3、**SpringBuilder** 可变字符串，可以对字符串进行添加等操作，因为SpringBuilder是不安全的所以我们在单线程中优先使用SpringBuilder其性能优于SpringBuffer。


| 构造器                           | 说明                                           |
| -------------------------------- | ---------------------------------------------- |
| public StringBuilder()           | 创建一个空白的可变的字符串对象，不包含任何内容 |
| public StringBuilder(String str) | 创建一个指定字符串内容的可变字符串对象         |

| 方法名称                              | 说明                                                |
| ------------------------------------- | --------------------------------------------------- |
| public StringBuilder append(任意类型) | 添加数据并返回StringBuilder对象本身                 |
| public StringBuilder reverse()        | 将对象的内容反转                                    |
| public int length()                   | 返回对象内容长度                                    |
| public String toString()              | 通过toString()就可以实现把StringBuilder转换为String |
| public void setLength(int length)     | 可以用于清空SB                                      |

# ============

# I Collection

Collection是单列集合的祖宗，它规定的方法（功能）是全部单列集合都会继承的。

| **方法名**                          | **说明**                         |
| ----------------------------------- | -------------------------------- |
| public boolean add(E e)             | 把给定的对象添加到当前集合中     |
| public void clear()                 | 清空集合中所有的元素             |
| public boolean remove(E e)          | 把给定的对象在当前集合中删除     |
| public boolean contains(Object obj) | 判断当前集合中是否包含给定的对象 |
| public boolean isEmpty()            | 判断当前集合是否为空             |
| public int size()                   | 返回集合中元素的个数。           |
| public Object[] toArray()           | 把集合中的元素，存储到数组中     |

## 迭代器

迭代器是用来**遍历集合**的专用方式(数组没有迭代器)，在Java中迭代器的代表是**Iterator**

```java
Iterator<String> it = lists.iterator(); //String类型集合的迭代器
while(it.hasNext()){
    String ele = it.next();
    System.out.println(ele);
}
```

默认指向第一个元素，索引0。注意迭代器**不能越界**

## 增强for遍历

数组也通用。

增强for遍历集合，本质就是迭代器遍历集合的简化写法。

```java
Collection<String> arr = new ArrayList<>();
...
    for(String s : arr) {
        System.out.println(s);
    }
```

## forEach遍历

| **方法名称**                                     | **说明**           |
| ------------------------------------------------ | ------------------ |
| default void forEach(Consumer<? super T> action) | 结合lambda遍历集合 |

```java
lists.forEach(s -> {
    System.out.println(s);
});
//  lists.forEach(s -> System.out.println(s));
//  lists.forEach(System.out::println);
```

## 遍历修改并发异常

遍历集合的同时又存在增删集合元素的行为时可能出现业务异常，这种现象被称之为**并发修改异常**问题。

**原因**：当前位置进行修改后，遍历到下个位置，过程可能会漏掉修改后的当前位置

**解决方案**：

1、数据删除后 i-- 回退位置

2、倒序遍历删除

3、用迭代器方法删除（不能用集合方法）

4、另外二个遍历方法无法解决

# II List

**List系列集合特点:**  有序，可重复，有索引

ArrayList：有序，可重复，有索引。

LinkedList：有序，可重复，有索引。

| **方法名称**                   | **说明**                               |
| ------------------------------ | -------------------------------------- |
| void add(int  index,E element) | 在此集合中的指定位置插入指定的元素     |
| E remove(int  index)           | 删除指定索引处的元素，返回被删除的元素 |
| E set(int index,E  element)    | 修改指定索引处的元素，返回被修改的元素 |
| E get(int  index)              | 返回指定索引处的元素                   |

## 遍历方法

①for循环（因为List集合有索引）

②迭代器 

③增强for循环

④Lambda表达式

# III ArrayList

1、创建ArrayList对象，代表一个集合容器,**集合的大小可变**，可自动扩容。先创建一个空数组，第一次加数据，扩容到默认长度**10**。后续每次扩容为size的1.5倍，即size + (size >> 1)

2、调用ArrayList提供的方法，对容器中的数据进行增删改查操作，**ArrayList**是泛型类。

3、ArrayList底层是基于**数组**存储数据的。

```java
ArrayList<Integer> arr = new ArrayList<>();
```

| 常用方法名                           | 说明                                   |
| ------------------------------------ | -------------------------------------- |
| public boolean add(E e)              | 将指定的元素添加到此集合的末尾         |
| public void add(int index,E element) | 在此集合中的指定位置插入指定的元素     |
| public E get(int  index)             | 返回指定索引处的元素                   |
| public int size()                    | 返回集合中的元素的个数                 |
| public E remove(int  index)          | 删除指定索引处的元素，返回被删除的元素 |
| public boolean remove(Object o)      | 删除指定的元素，返回删除是否成功       |
| public E set(int index,E element)    | 修改指定索引处的元素，返回被修改的元素 |

1、根据**索引查询数据快**，查询数据通过地址值和索引定位，查询任意数据耗时相同。

2、**增删数据效率低**：可能需要把后面很多的数据进行前移。

# III LinkedList

LinkedList底层是基于链表存储数据的。基于双链表实现的。

链表中的数据是一个一个独立的结点组成的，结点在内存中是不连续的，每个结点包含数据值和下一个结点的地址。

1、**查询慢**，无论查询哪个数据都要从头开始找。

2、**链表增删相对快**

3、双链表**对首尾元素进行增删改查的速度是极快的。**

| **方法名称**               | **说明**                         |
| -------------------------- | -------------------------------- |
| public  void addFirst(E e) | 在该列表开头插入指定的元素       |
| public  void addLast(E e)  | 将指定的元素追加到此列表的末尾   |
| public  E getFirst()       | 返回此列表中的第一个元素         |
| public  E getLast()        | 返回此列表中的最后一个元素       |
| public  E removeFirst()    | 从此列表中删除并返回第一个元素   |
| public  E removeLast()     | 从此列表中删除并返回最后一个元素 |

1、适合做队列Queue

2、适合做栈

# II Set

Set系列集合特点：

 无序，添加数据的顺序和获取出的数据顺序不一致； 不重复； 无索引;

HashSet : 无序、不重复、无索引。

LinkedHashSet：**有序**、不重复、无索引。

TreeSet：**排序**、不重复、无索引。

Set要用到的常用方法，基本上就是**Collection**提供的！！**自己几乎没有额外新增一些常用功能！**

# III HashSet

基于**哈希表存储数据**的。

JDK8之前，哈希表 = 数组+链表

JDK8开始，哈希表 = 数组+链表+**红黑树**

## **哈希表**

是一种增删改查数据，性能都较好的数据结构

创建默认长度为**16**的数组，默认加载因子为**0.75**，数组名为**table**

数据量超过**长度 * 加载因子**（默认12）就**扩容为原来的2倍**（32）

**JDK8**开始，当链表长度超过**8**，且**数组长度>=64**时，自动将链表转成**红黑树**

## **红黑树**

红黑树，就是可以自平衡的二叉树

红黑树是一种增删改查数据性能相对都较好的结构。

## **哈希值**

就是一个int类型的**随机值**，Java中每个对象都有一个**哈希值**。

Java中的所有对象，都可以调用Obejct类提供的hashCode方法，返回该对象自己的哈希值。

```java
public int hashCode()：返回对象的哈希码值
```

同一个对象**多次调用**hashCode()方法返回的哈希值是**相同**的。

不同的对象，它们的哈希值大概率不相等，但也有可能会相等(**哈希碰撞**)。

## 对象去重

重复机制：hashCode()和equals()方法计算都相同为重复

重写hashCode方法：

```java
@Override
public int hashCode () {
    return Object.hash(name, age, address, phone);
}//改为通过对象属性计算hash值
```

重写equals方法，判断内容相同

# III LinkedHashSet

**有序**、不重复、无索引。依然是基于**哈希表**(数组、链表、红黑树)实现的。

但是，它的每个元素都额外的多了一个**双链表**的机制记录它前后元素的位置。

# III TreeSet

特点：不含重复、无索引、可排序（默认升序排序 ，按照元素的大小，由小到大排序）
底层是基于**红黑树**实现的**排序**。

对于数值类型：Integer , Double，默认按照数值本身的**大小**进行升序排序。

对于字符串类型：默认按照**首字符**的编号升序排序。

对于自定义类型如Student**对象**，TreeSet默认是无法直接排序的。

## 排序规则

TreeSet集合存储**自定义类型**的对象时，必须指定排序规则，支持如下两种方式来指定比较规则。

1、让自定义的类（如学生类）**实现**Comparable接口，**重写**里面的compareTo方法来指定比较规则。

2、通过调用**TreeSet**集合有参数构造器，可以设置Comparator对象（比较器对象，用于指定比较规则）。

```java
Set<Teacher> teachers = new TreeSet<>((o1, o2) ->
      Double.compare(o1.getSalary, o2.getSalary));
```

# 总结

1、如果希望记住元素的添加顺序，需要存储重复的元素，又要频繁的根据索引查询数据？

用**ArrayList**集合（有序、可重复、有索引），底层基于数组的。**（常用）**

2、如果希望记住元素的添加顺序，且增删首尾数据的情况较多？

用**LinkedList**集合（有序、可重复、有索引），底层基于双链表实现的。

3、如果不在意元素顺序，也没有重复元素需要存储，只希望增删改查都快？

用**HashSet**集合（无序，不重复，无索引），底层基于哈希表实现的。 **（常用）**

4、如果希望记住元素的添加顺序，也没有重复元素需要存储，且希望增删改查都快？

用**LinkedHashSet**集合（有序，不重复，无索引）， 底层基于哈希表和双链表。

5、如果要对元素进行排序，也没有重复元素需要存储？且希望增删改查都快？

用**TreeSet**集合，基于红黑树实现。

# ============

# I Map

Map集合也被叫做“键值对集合”，格式：{key1=value1 , key2=value2 , key3=value3 , ...}

Map集合的所有键是不允许重复的，但值可以重复，键和值是一一对应的，每一个键只能找到自己对应的值

HashMap: 无序、不重复、无索引；（用的最多）

LinkedHashMap :由键决定的特点：有序、不重复、无索引。

TreeMap:按照大小默认升序排序**、**不重复、无索引。

| **方法名称**                               | **说明**                              |
| ------------------------------------------ | ------------------------------------- |
| public V put(K key,V value)                | 添加元素                              |
| public int size()                          | 获取集合的大小                        |
| public void clear()                        | 清空集合                              |
| public boolean isEmpty()                   | 判断集合是否为空，为空返回true , 反之 |
| public V get(Object key)                   | 根据键获取对应值                      |
| public V remove(Object key)                | 根据键删除整个元素                    |
| public boolean containsKey(Object  key)    | 判断是否包含某个键                    |
| public boolean containsValue(Object value) | 判断是否包含某个值                    |
| public Set<K> keySet()                     | 获取全部键的集合                      |
| public Collection<V> values()              | 获取Map集合的全部值                   |

**遍历方法**

1、先获取Map集合全部的键，再通过遍历键来找值

2、把“键值对“看成一个整体进行遍历（难度较大）

```java
Set< Map.Entry<String, Double > entries = map.entrySet();
for (Map.Entry<String, Double> entry : entries) {
    String key = entry.getKey();
    double value = entry.getValue();
    System.out.println(key + "====>" + value);
}
```

3、forEach遍历

```JAVA
map.forEach((k , v) -> {
    System.out.println(k +"----->" + v);
});

```

# II HashMap

HashMap跟HashSet的底层原理是一模一样的，都是基于哈希表实现的。

**Set**系列集合的底层就是基于**Map**实现的，只是Set集合中的元素只要键数据，不要值数据而已。

# II LinkedMap

底层数据结构依然是基于哈希表实现的，只是每个键值对元素又额外的多了一个双链表的机制记录元素顺序(保证有序)。**LinkedHashSet**集合的底层原理就是**LinkedHashMap**。

# II TreeMap

特点：不重复、无索引、可排序(按照键的大小默认升序排序，**只能对键排序**)

原理：TreeMap跟TreeSet集合的底层原理是一样的，都是基于红黑树实现的排序。

**TreeMap**集合同样也支持两种方式来指定排序规则

1、让类实现Comparable接口，重写比较规则。

2、TreeMap集合有一个有参数构造器，支持创建Comparator比较器对象，以便用来指定比较规则。

# ============

# 其他API

```java
int c = Math.max(a, b);//求两者中大的数
int d = Math.min(a, b);//求两者中小的数
int pd = Double.compare(0.11, 0.9);//小数判断大小返回pd = -1
long time = System.currentTimeMillis();//此刻系统时间毫秒值，从1970-1-1 0点走过的毫秒
@Data //添加get set 方法注解
@AllArgsConstructor//添加有参构造器
@NoArgsConstructor//添加无参构造器
Thread.sleep(200);//线程休眠200毫秒

System.out.printf("%s", "hello world"); // 输出 "hello world"
System.out.printf("%d %o %x %X", 10, 10, 10, 10); // 输出 "10 12 a A"
System.out.printf("%.2f %e %E", 3.1415926, 3.1415926, 3.1415926); // 输出 "3.14 3.141593e+00 3.141593E+00"
System.out.printf("%b", true); // 输出 "true"
System.out.printf("%c", 'a'); // 输出 "a"
```

## BigDecimal

用于解决浮点型运算时，出现结果失真的问题。

| **构造器**                                         | **说明**                   |
| -------------------------------------------------- | -------------------------- |
| public BigDecimal(double val) 注意：不推荐使用这个 | 将 double转换为 BigDecimal |
| public BigDecimal(String val)                      | 把String转成BigDecimal     |

| **方法名**                                                   | **说明**                     |
| ------------------------------------------------------------ | ---------------------------- |
| public static BigDecimal valueOf(double val)                 | 转换一个 double成 BigDecimal |
| public BigDecimal add(BigDecimal b)                          | 加法                         |
| public BigDecimal subtract(BigDecimal b)                     | 减法                         |
| public BigDecimal multiply(BigDecimal b)                     | 乘法                         |
| public BigDecimal divide(BigDecimal b)                       | 除法                         |
| public BigDecimal divide (另一个BigDecimal对象，精确几位，舍入模式) | 除法、可以控制精确到小数几位 |
| public double doubleValue()                                  | 将BigDecimal转换为double     |

## 数组 Arrays

```java
// 数组排序：
Arrays.sort(数组名);
// 数组打印（转为String类型）：
Arrays.toString(数组名);
// 多维数组打印：
Arrays.deepToString(数组名);
// 升序数组二分查找：
Arrays.binarySearch(数组名, 元素);
// 拷贝数组
Arrays.copyOf(数组，新数组长度);
// 指定拷贝的起始索引和结束索引（结束索引的元素不被拷贝）（包头不包尾）
Arrays.copyOfRange(数组，起始索引，结束索引);
```

## 堆 PriorityQueue

PriorityQueue是**线程不安全**的，PriorityBlockingQueue是**线程安全**的

PriorityQueue中放置的元素必须要**能够比较大小**

 ```java
 import java.util.PriorityQueue
 ```

构造器：

| 构造器                                    | 功能介绍                                                     |
| ----------------------------------------- | ------------------------------------------------------------ |
| PriorityQueue()                           | 创建一个空的优先级队列，默认容量是11                         |
| PriorityQueue(int initialCapacity)        | 创建一个初始容量为 initialCapacity 的优先级队列。注意：initialCapacity 不能小于1，否则会抛出 IllegalArgumentException 异常 |
| PriorityQueue(Collection <? extends E> c) | 用一个集合来创建优先级队列                                   |

方法：

| 函数名             | 功能介绍                                                     |
| ------------------ | ------------------------------------------------------------ |
| boolean offer(E e) | 插入元素 e，插入成功返回 true，如果 e 对象为空，抛出 NullPointerException 异常，时间复杂度为 O(log2N) ，注意：空间不够时会自动扩容 |
| E peek()           | 获取优先级最高的元素，如果优先级队列为空，返回 null          |
| E poll()           | 移除优先级最高的元素并返回，如果优先级队列为空，返回 null    |
| int size()         | 获取有效元素的个数                                           |
| void clean()       | 清空                                                         |
| boolean isEmpty()  | 检测优先级队列是否为空，空返回 true                          |

```java
PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
// 倒序排列
```

## BigInteger

**支持任意精度的整数**，也就是说在运算中 BigInteger 类型可以准确地表示任何大小的整数值而不会丢失任何信息。

```java
String str = new String();
BigInteger n = new BigInteger(str);
return n.isProbablePrime(10); // 质数判断
int a = n.intValue(); // 转换为int类型
```

