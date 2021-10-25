package cn.eakoo.desensitize.annotation;

import java.lang.annotation.*;
import cn.eakoo.desensitize.constant.SensitiveType;

/**
 * 自定义脱敏注解
 *
 * @author rui.zhou
 * @date 2021/10/17 22:22
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Desensitize {

    /**
     * 脱敏字段枚举
     */
    String value() default SensitiveType.ID_CARD;

}
