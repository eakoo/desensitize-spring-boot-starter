<div align="center">
<br/>
<br/>
<img src="https://pandao.github.io/editor.md/images/logos/editormd-logo-180x180.png" width="90px" style="margin-top:30px;"/>
  <h1 align="center">
    desensitize-spring-boot-starter
  </h1>
  <h4 align="center">
    敏感字段脱敏或者增强 starter
  </h4> 

[Demo](http://10.142.146.37:8081/rui.zhou/desensitize-demo)


</div>
<div align="center">
<p align="center">
    <a href="#">
        <img src="https://img.shields.io/badge/desensitize--spring--boot--starter-1.0.0-green" alt="Pear Admin Layui Version">
    </a>
    <a href="#">
        <img src="https://img.shields.io/badge/springboot-2.5.6-green" alt="Jquery Version">
    </a>
</p>
</div>

# 使用教程

> 引用依赖

```xml
<project>

    <dependency>
        <groupId>com.cignacmb.ibss</groupId>
        <artifactId>desensitize-spring-boot-starter</artifactId>
        <version>1.0.0</version>
    </dependency>
    
    <repositories>
        <repository>
            <id>nexus</id>
            <name>Team Nexus Repository</name>
            <url>http://XXXXXXX/nexus/content/repositories/releases/</url>
            <releases>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </releases>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
</project>
```
> 配置策略, 自带策略/覆盖/新增脱敏策略(选择一种即可)

```java
@Configuration
public class DesensitizeStrategyConfig {

    //方式一 自带策略
    @Bean
    public Sensibler sensibler() {
        return Sensibler.getInstance();
    }
    
    //方式二 覆盖脱敏策略
    @Bean
    public Sensibler sensibler() {
        return Sensibler.getInstance()
                .addDesensitizeStrategy(SensitiveType.EMAIL, s -> s.replaceAll("(^\\w)[^@]*(@.*$)", "$1****$2"))
                .addDesensitizeStrategy(SensitiveType.MOBILE, s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"))
                .addDesensitizeStrategy(SensitiveType.ID_CARD, s -> s.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*"));
    }
    
    //方式三 新增脱敏策略
    @Bean
    public Sensibler sensibler() {
        return Sensibler.getInstance()
                .addDesensitizeStrategy("test1", s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"))
                .addDesensitizeStrategy("test2", s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"))
                .addDesensitizeStrategy("test3", s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
    }
    
}
```

> 配置启动类, 扫描"cn.eakoo.desensitize"包下面的组件 

```

@ComponentScan(value = "cn.eakoo.desensitize")

```

> 敏感字段脱敏或者增强

1. @Desensitize 注解标记的字段表示对改字段进行脱敏操作
2. @Desensitize 注解自带六种字段脱敏策略 (详细请看SensitiveType)
3. 如果你觉得自带的脱敏策略不够优雅或者不能满足业务需求, 那么请你大胆用你牛逼的技术把自带策略覆盖了
4. 如果你觉得自带的脱敏策略没有你想要的, 那么你也可以根据业务需求新增适合自己业务的策略
5. 字段类型为实体类时, 默认是不对实体类里面带有@Desensitize 注解的字段进行脱敏操作 
6. 如果想要对字段类型为实体类里面带有@Desensitize 注解的字段进行脱敏操作, 那么请在该实体类上加上@Sensitive 注解
7. @Sensitive 注解标记的字段表示对象里面有敏感数据需要进行脱敏操作, 有没有似曾相识的感觉? @Validated 和 @Valid

> 优点

1. 这是一个弹性的组件, 既提供基础的脱敏策略, 也可以根据自己的业务扩展
2. 对业务代码没有侵入, 功能开启和关闭并不影响业务代码

