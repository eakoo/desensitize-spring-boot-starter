package cn.eakoo.desensitize.annotation;

import java.lang.annotation.*;

/**
 * 被 @Sensitive 标记的对象, 表示该对象有敏感数据
 * 与 @Desensitize 注解一起使用, 表示对子对象进行脱敏
 *
 * @author rui.zhou
 * @date 2021/10/17 22:34
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sensitive {
}
