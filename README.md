# 基础篇

## SpringBoot整合

### MyBatis

1. 勾选依赖
   * SQL Driver
   * MyBatis Framework
2. 配置数据库连接信息
   * application.yml文件中数据库信息完善
3. 编写实体类pojo以及mapper接口

### MyBatisPlus

1. 添加Mp依赖

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.14</version>
</dependency>
```

2. dao层接口继承`BaseMapper<T>`类

```java
@Mapper
public interface BookDao extends BaseMapper<T> {}
```

### Druid

1. 导入坐标
2. 修改yml文件

```yml
spring:
  datasource:
    druid:
      driver-class-name: com.mysql.jdbc.Driver
      url: jdbc:mysql://localhost:3306/seraphim
      username: root
      password: 1234
```



# 运维篇

## 多环境开发

```yml
# 应用环境
# 公共配置
spring:
  profiles:
    active: dev

---
# 设置环境
# 生产环境

spring:
  config:
    activate:
      on-profile: pro

server:
  port: 80


---
# 开发环境
spring:
  config:
    activate:
      on-profile: dev

server:
  port: 81


---
# 测试环境
spring:
  config:
    activate:
      on-profile: test

server:
  port: 82
```

![image-20251009114721399](C:\Users\Qingfeng\AppData\Roaming\Typora\typora-user-images\image-20251009114721399.png)



# 开发篇

## 启动热部署

## 第三方Bean属性绑定

```java
@Data
@Component
@ConfigurationProperties(prefix = "servlet")
public class ServletConfig {
    private String ipAddress;
    private int port;
    private long timeout;
}
```

```yml
servlet:
  ipAddress: "111"
  port: 55
  timeout: 999
```

> `@ConfigurationProperties`支持宽松绑定，即yml文件中变量属性名称在读取时忽略大小写-_等，但`@Vaule`不行

```yml
@Value("${servlet.ipAddress}")
    private String string;
```

> @Value注入属性值

## 属性校验

![image-20251009164717744](C:\Users\Qingfeng\AppData\Roaming\Typora\typora-user-images\image-20251009164717744.png)

## 加载测试专用属性

```java
@SpringBootTest(properties = {"servlet.port=value1"},args = {"--servlet.port=value2"})
class Springboot13ConfigurationApplicationTests {

   @Value("${servlet.port}")
   private String msg;

    @Test
    void contextLoads() {
        System.out.println(msg);
    }

}
```

## 虚拟MVC调用发送请求

```java
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MvcTest {
    @Test
    public void testWeb(@Autowired MockMvc mvc) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books");
        ResultActions action = mvc.perform(builder);
    }
}
```

## 每日三问

1. 什么是持久化技术？

### ✅ 一句话概念：

> **持久化（Persistence）** 是指把**数据从内存保存到磁盘（或数据库）**中，以便下次系统启动时还能继续使用。

也就是说：

- 程序运行时，数据一般存放在内存里；
- 程序关闭后，内存数据会消失；
- 我们用“持久化技术”把这些数据写入 **数据库 / 文件 / 磁盘**，让它“长期保存”。

------

### 📘 举个例子：

假如你写了一个学生管理系统：

```mysql
Student stu = new Student("张三", 20);
```

这个对象存在于内存中，当程序结束就没了。
 如果想保存到数据库，就要用持久化技术，比如：

```mysql
INSERT INTO student(name, age) VALUES('张三', 20);
```

下次启动程序可以从数据库中再读回来：

```mysql
SELECT * FROM student;
```

1. 什么是springmvc？

>  springmvc是基于MVC模式的web开发框架，是Spring框架的一部分，用来请求web请求与响应。

* 控制层/表现层(Controller)

```javascript
浏览器请求
   ↓
DispatcherServlet（前端控制器）
   ↓
HandlerMapping（找到对应的Controller方法）
   ↓
HandlerAdapter（执行Controller方法）
   ↓
Controller（执行业务逻辑）
   ↓
Service → Mapper → 数据库
   ↓
Controller返回结果（Model或JSON）
   ↓
ViewResolver（渲染页面或返回数据）
   ↓
响应给浏览器
```

📍其中：

- `DispatcherServlet` 是 SpringMVC 的“总指挥”
- `Controller` 是开发者写的控制逻辑（你接触最多的部分）
- SpringMVC 管理整个「请求到响应」的流程。



## 整合第三方技术

### 缓存

`cache`：高速缓存

![image-20251010160437125](C:\Users\Qingfeng\AppData\Roaming\Typora\typora-user-images\image-20251010160437125.png)

在pom.xml中添加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

在业务层代码上添加注解：

```java
@Override
@Cacheable(value = "cacheSpace",key = "#id")
public Book getById(Integer id) {
    return bookDao.selectById(id);
}
```

| 项目                     | 含义                                                         |
| ------------------------ | ------------------------------------------------------------ |
| `@Cacheable`             | 表示：在执行方法前，先查缓存；如果缓存有数据，就直接返回缓存内容，不执行方法体 |
| `value = "cacheSpace"`   | 缓存的命名空间（缓存名/缓存区名）                            |
| `key = "#id"`            | 缓存的键（通常用入参）                                       |
| `bookDao.selectById(id)` | 只有当缓存中没命中时才会真正执行的数据库查询                 |

### redis缓存

配置redis

```yml
spring:
  cache:
     type: redis
         redis:
           time-to-live: 10s
```

导入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```



### jetcache缓存

添加依赖

```xml
<dependency>
    <groupId>com.alicp.jetcache</groupId>
    <artifactId>jetcache-starter-redis</artifactId>
    <version>2.7.2</version>
</dependency>
```

修改配置(**远程方案**)

```yml
jetcache:
  remote:
    sms:
     type: redis
     host: localhost
     port: 6379
     poolConfig:
       maxTotal: 50
```

写代码

```java
@CreateCache(area = "sms", name = "jetCache_",expire = 10,timeUnit = TimeUnit.SECONDS)
private Cache<String,String> jetCache;

@Override
public String sendCodeToSMS(String tele) {
    String code = codeUtils.generator(tele);
    jetCache.put(tele,code);
    return code;
}

@Override
public boolean checkCode(SMSCode smsCode) {
    String code = jetCache.get(smsCode.getTele());
    return smsCode.getCode().equals(code);
}
```

![image-20251010203806936](C:\Users\Qingfeng\AppData\Roaming\Typora\typora-user-images\image-20251010203806936.png)



## 监控

```xml
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-client</artifactId>
    <version>3.5.5</version>
</dependency>
```

```yml
spring:
  boot:
    admin:
      client:
        url: http://localhost:8080

server:
  port: 8081

management:
  endpoint:
    health:
      show-details: always
  endpoints:
    web:
      exposure:
        include: "*"

```

![image-20251012144153283](C:\Users\Qingfeng\AppData\Roaming\Typora\typora-user-images\image-20251012144153283.png)

# 原理篇

## bean的加载方式

![image-20251012165545436](C:\Users\Qingfeng\AppData\Roaming\Typora\typora-user-images\image-20251012165545436.png)

## bean加载控制

***编程式***

```java
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        try {
            Class<?> clazz = Class.forName("com.itheima.bean.Mouse");
            if(clazz != null) return new String[]{"com.itheima.bean.Cat"};
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            return new String[0];
        }
        return null;
    }
}
```

***注解式***

```java
@Bean
@ConditionalOnClass(name = "com.mysql.jdbc.Driver")
public DataSource dataSource(){
    return new DruidDataSource();
}
```



# SpringBoot启动流程

![image-20251013164341170](C:\Users\Qingfeng\AppData\Roaming\Typora\typora-user-images\image-20251013164341170.png)



1. As can ---- from Table II, participation figures have been steadily falling since 1970.
2. Different authors have ---- the President’s actions in different ways.
3. Mendel attempted to devise a system for **----** the many different types of pea plant that he grew.
4. It is often most effective to **----** your data in a chart or table.
5. The data we have collected **----** that there has been a downward trend with regard to job satisfaction over the last 50 years.
6. The aim of the research is to **----** a new software application which will help aviation engineers design more sophisticated aircraft.
7. The archaeologists should be able to use carbon dating techniques to **----** exactly how old the bones are.
8. Charles Darwin **----** to explain the existence of different species in terms of evolution
