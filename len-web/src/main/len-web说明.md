# len-web目录结构

1. 目录结构

 ```
   ├─src
   │  └─main
   │      ├─java
   │      │  ├─com
   │      │  │  └─len
   │      │  │      │  LenApplication.java   启动类
   │      │  │      │
   │      │  │      └─config
   │      │  │              BeanFactoryImpl.java   工厂设置
   │      │  │              BeanName.java
   │      │  │              DruidConfig.java       数据源
   │      │  │              FreeMarkerConfig.java  freemarker（工作流）配置类
   │      │  │              FreemarkerShiroConfig.java
   │      │  │              LenFilter.java
   │      │  │              LogConfig.java
   │      │  │              MyBatisPlusConfig.java
   │      │  │              MyModularRealmAuthenticator.java
   │      │  │              PageHelperConfig.java
   │      │  │              ShiroConfig.java
   │      │  │              ShiroSessionManager.java
   │      │  │              SocketConfig.java
   │      │  │              SwaggerConfig.java
   │      │  │              TransactionalConfig.java
   │      │  │              WebMvcConfig.java
   │      │  │              ZBeanFactory.java
   │      │  │
   │      │  └─test     测试案例自定义
   │      │
   │      └─resources
   │          │
   │          ├─auto-config
   │          │      mybatis-config.xml
   │          │
   │          └─ehcache
   │                  ehcache.xml
   │
 ```

## 优化sql

1. 执行PMD代码检查

2. ##### 使用expl ain查看自己编写的Sql执行情况。

3. ##### 写的Sql尽量满足最左索引匹配原则使用索引。

4. ##### 查询SQL尽量不要使用select *，而是具体字段

5. ##### where中使用默认值代替null

6. ##### 首先尽量避免模糊查询，如果必须使用，不采用全模糊查询，也应尽量采用右模糊查询， 即`like ‘…%’`，是会使用索引的；

7. ##### 避免在where子句中使用!=或<>操作符

   使用`!=`和`<>`很可能会让索引失效

   应尽量避免在`where`子句中使用`!=`或`<>`操作符，否则引擎将放弃使用索引而进行全表扫描

8. ##### 避免在where子句中使用 or 来连接条件    使用union all  例如

   ```
   SELECT?*?FROM?a WHERE?id=1?UNION?ALL SELECT?*?FROM? a  WHERE?salary=5000 
   ```

   原因：

    - 使用`or`可能会使索引失效，从而全表扫描；

    - 对于`or`没有索引的`salary`这种情况，假设它走了`id`的索引，但是走到`salary`查询条件时，它还得全表扫描；

      #####  