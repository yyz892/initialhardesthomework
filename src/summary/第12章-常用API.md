# 第12章：常用API

---

## 12.1 String 字符串

### 为什么String是不可变的？

```java
String s = "Hello";
s = s + " World";  // 看起来像"改了"s，实际是创建了新对象
// 旧的"Hello"对象还在堆里，s重新指向了新的"Hello World"
```

**不可变的设计原因**：
1. **安全**：密码、用户名等敏感信息传给别人，别人改不了原字符串
2. **线程安全**：多线程同时读同一个字符串，不用担心被改
3. **字符串常量池**：相同内容的字符串可以共享，省内存
4. **HashMap的key**：如果字符串可变，用它做key的HashMap就会乱套

```java
// 字符串常量池：相同字面量的字符串指向同一对象
String s1 = "hello";
String s2 = "hello";
System.out.println(s1 == s2);  // true！指向同一个对象（常量池）
// 不用new同样的字符串，直接复用已有的

String s3 = new String("hello");
System.out.println(s1 == s3);  // false，new一定创建新对象
// 所以比较字符串内容用 equals，不要用 ==
```

### 常用方法速查

```java
String s = "Hello";

// 判断
s.equals("Hello")                  // 内容相等 → true（区分大小写）
s.equalsIgnoreCase("hello")        // 忽略大小写 → true

// 获取
s.length()                        // 长度 → 5
s.charAt(0)                       // 第0个字符 → 'H'

// 截取
s.substring(0, 3)                 // 从0到3(不含) → "Hel"
s.substring(3)                    // 从3到末尾 → "lo"

// 替换
s.replace("l", "L")               // 全部替换 → "HeLLo"

// 转换
s.toUpperCase()                   // → "HELLO"
s.toLowerCase()                   // → "hello"
s.toCharArray()                   // → {'H','e','l','l','o'}

// 分割
"a,b,c".split(",")                // → ["a","b","c"]

// 去空格
"  hello  ".trim()                // → "hello"
```

### 字符串算法实例

```java
// 1. 统计字符类型（验证码/密码校验常用）
String str = "Hello123";
int upper = 0, lower = 0, digit = 0;
for (int i = 0; i < str.length(); i++) {
    char c = str.charAt(i);
    if (c >= 'A' && c <= 'Z') upper++;
    else if (c >= 'a' && c <= 'z') lower++;
    else if (c >= '0' && c <= '9') digit++;
}

// 2. 手机号脱敏
String phone = "13812345678";
String masked = phone.substring(0, 3) + "****" + phone.substring(7);
// "138****5678"

// 3. 反转字符串（判断回文）
String original = "abcba";
String reversed = new StringBuilder(original).reverse().toString();
boolean isPalindrome = original.equals(reversed);
```

---

## 12.2 StringBuilder

### 为什么需要StringBuilder？

```java
// ❌ 用String拼接（循环里是灾难）
String s = "";
for (int i = 0; i < 10000; i++) {
    s = s + i;    // 每次循环都创建新String对象！10000个对象被丢弃！
}
// 内存中产生了大量垃圾，GC频繁回收，性能极差

// ✅ 用StringBuilder
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 10000; i++) {
    sb.append(i);  // 只在同一个StringBuilder对象里追加，不创建新对象
}
String result = sb.toString();  // 最后一次性转成String
```

**原理**：
- String：不可变，拼接 = 创建新对象 + 丢弃旧对象
- StringBuilder：内部是一个可变字符数组，append只是往数组里写数据

```java
StringBuilder sb = new StringBuilder();
sb.append("Hello");            // 追加
sb.append(" World");
sb.reverse();                  // 反转
sb.insert(5, " Java");         // 指定位置插入
String s = sb.toString();      // 转回String
```

### 什么时候用哪个？

| 场景 | 用什么 |
|------|--------|
| 字符串不变 | String |
| 少量拼接（3~5个） | String（编译器自动优化成StringBuilder） |
| 循环拼接 | **必须用StringBuilder** |
| 多线程拼接 | StringBuffer（线程安全版，慢一点） |

---

## 12.3 ArrayList 集合

### 为什么需要ArrayList？

```java
// 数组的痛点：长度固定
int[] arr = new int[3];
arr[0] = 10; arr[1] = 20; arr[2] = 30;
// arr[3] = 40;  // ❌ 数组已满，存不进去！

// 而你游戏项目中的用户注册：
// 你根本不知道有多少人会注册 → 必须用ArrayList
ArrayList<User> users = new ArrayList<>();
users.add(new User("张三"));
users.add(new User("李四"));
// ... 无限添加，自动扩容
```

### 常用操作

```java
ArrayList<String> list = new ArrayList<>();

// 增
list.add("张三");                // 末尾添加 → true
list.add(0, "李四");             // 指定位置添加

// 删
list.remove(0);                  // 按索引删除 → 返回被删元素
list.remove("张三");             // 按内容删除 → 返回true/false

// 改
list.set(0, "王五");             // 修改指定位置元素

// 查
String name = list.get(0);       // 获取 → "王五"
int size = list.size();          // 大小
boolean has = list.contains("张三"); // 是否包含

// 遍历
for (int i = 0; i < list.size(); i++) {
    System.out.println(list.get(i));
}
```

### ArrayList 的泛型

```java
// 泛型 <E>：限制集合里只能存一种类型
ArrayList<String> strings = new ArrayList<>();
strings.add("hello");
// strings.add(123);  // ❌ 编译报错！类型安全

// 为什么需要泛型？
// 没有泛型时：取出来是Object，需要强制类型转换，容易出错
ArrayList list = new ArrayList();       // 原始类型，不推荐
list.add("hello");
String s = (String) list.get(0);        // 必须强转

// 有泛型时：编译器就知道里面是String
ArrayList<String> list2 = new ArrayList<>();
String s2 = list2.get(0);  // 不需要强转，直接就是String
```

### ArrayList vs 数组：游戏项目中的选择

```java
// 验证码字符池 → 数组（固定62个字符，不会变）
char[] pool = new char[62];

// 用户列表 → ArrayList（用户数量不确定）
ArrayList<User> userList = new ArrayList<>();

// 敌人类型 → 数组或枚举（固定几种）
Enemy[] enemies = { ... };
// 或
enum EnemyType { GOBLIN, SKELETON, DRAGON, BOSS }

// 战斗日志 → ArrayList（不确定打几回合）
ArrayList<String> battleLog = new ArrayList<>();
```

**一句话**：**长度确定用数组，长度可变用ArrayList**。

### 为什么不用数据库存用户数据？

这个问题你在游戏项目里一定想到过：

```java
// 当前阶段不用数据库的原因：
// 1. 学习顺序：Java核心 → 数据库(MySQL) → 框架(Spring) → 项目整合
//    现在处于"Java核心"阶段，数据库会在后续"苍穹外卖"项目中深入学习
// 2. 学习成本：数据库需要安装MySQL、学习SQL、配置JDBC连接
//    现阶段的目标是掌握Java面向对象和集合框架
// 3. 简化教学：ArrayList足够演示"存储→查找→遍历"的核心逻辑
// 4. 实际项目会用：用户表存MySQL + 缓存用Redis
//    但那是你学完框架之后的事了
```
