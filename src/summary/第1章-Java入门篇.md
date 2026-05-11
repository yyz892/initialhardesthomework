# 第1章：Java入门篇

---

## 1.1 Java跨平台原理

### 为什么Java能跨平台？

C/C++ 编译出来的是机器码（.exe），不同操作系统的机器码不一样，换个系统就得重新编译。

```java
// C语言：不同平台需要不同编译器，编译出不同文件
// Windows → .exe
// Linux   → .out
// Mac     → .app

// Java：编译一次，到处运行
// 源代码(.java) → javac → 字节码(.class) → 由不同平台的JVM执行
```

Java的做法：不在操作系统上直接跑，而是在JVM（Java虚拟机）上跑。每个平台装自己平台的JVM，JVM负责把同一份字节码翻译成本平台的机器码。**一次编译，到处运行**。

### 为什么Java选择虚拟机方案？

- 不用为每个平台单独编译和测试
- 企业级应用部署到不同服务器不用改代码
- 牺牲一点性能换跨平台（而且JIT编译器的进步让性能差距越来越小）

---

## 1.2 JDK / JRE / JVM 的关系

```
JDK (Java开发工具包)
├── JRE (Java运行环境)
│   ├── JVM (Java虚拟机)        ← 执行字节码
│   └── 核心类库 (String, System...)  ← 写代码用的基础类
└── 开发工具 (javac, java, jar...)     ← 编译、运行、打包
```

| 概念 | 干什么的 | 谁需要 |
|------|---------|--------|
| JDK | 开发+运行 | 程序员 |
| JRE | 运行 | 普通用户（运行Java程序） |
| JVM | 执行字节码 | JDK和JRE都包含 |

---

## 1.3 环境变量

### 为什么需要配置环境变量？

在命令行输入 `java` 时，系统怎么知道去哪里找 java.exe？

```
// 不配Path：每次都要写全路径
C:\Program Files\Java\jdk-17\bin\java HelloWorld

// 配了Path：系统自动去Path里的路径找
java HelloWorld
```

```
JAVA_HOME = C:\Program Files\Java\jdk-17    → 给其他工具用的
Path      = %JAVA_HOME%\bin                  → 让命令行找到java/javac
```

**为什么用 JAVA_HOME 而不是直接写死路径？**
- 很多工具（Maven、Tomcat、IDEA）默认找 JAVA_HOME
- 升级JDK只需改 JAVA_HOME，不用到处改

---

## 1.4 HelloWorld 逐行解析

```java
public class HelloWorld {                      // ①
    public static void main(String[] args) {   // ②
        System.out.println("Hello, World!");    // ③
    }
}
```

| 行 | 解释 | 为什么这样写 |
|----|------|------------|
| ① | `public` 公开的，`class` 定义类，`HelloWorld` 类名必须=文件名 | Java规定：public类的类名必须和文件名一致，方便编译器查找 |
| ② | `main` 是程序入口 | JVM启动后找main方法执行，这是Java语言规范写死的约定 |
| ③ | `System.out.println()` | System是java.lang包下的类（自动导入），out是标准输出流，println是打印并换行 |

### 为什么main方法要写成 public static void？

```java
public  → JVM需要从外部调用它，必须是公开的
static  → JVM还没创建任何对象，只能通过类名直接调用静态方法
void    → 程序结束不代表要返回什么值给操作系统
String[] args → 运行时可以传参数进来 java HelloWorld arg1 arg2
```

---

## 1.5 注释

```java
// 单行注释：给这行代码做说明

/*
   多行注释
   写比较长的解释时用
*/

/**
 * 文档注释
 * 可以用javadoc工具生成HTML文档
 * @author 张三
 * @version 1.0
 */
```

### 为什么要写注释？

- 代码是给计算机看的，注释是给人看的
- 一个月后回来看自己写的代码，没有注释可能看不懂
- 团队协作时别人能理解你的意图
- 但不要为了注释而注释：`int age = 18; // 定义年龄变量` 这种废话不要写
