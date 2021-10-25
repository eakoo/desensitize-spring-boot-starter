package cn.eakoo.desensitize.handler;

import cn.eakoo.desensitize.Sensibler;
import cn.eakoo.desensitize.annotation.Desensitize;
import cn.eakoo.desensitize.annotation.Sensitive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.lang.reflect.Field;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;

/**
 * @author rui.zhou
 * @date 2021/10/18 11:36
 **/
@RestControllerAdvice
public class HttpRespAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger logger = LoggerFactory.getLogger(HttpRespAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try{
            if (body == null) {
                return null;
            }
            Class<?> aClass = body.getClass();
            if (AbstractCollection.class.isAssignableFrom(aClass) && !AbstractMap.class.isAssignableFrom(aClass)) {
                for(Object object : (Collection<?>)body){
                    this.desensitizeObject(object);
                }
            } else {
                this.desensitizeObject(body);
            }
        }catch(Exception e){
            logger.error("response field desensitize fail",e);
        }
        return body;
    }

    /**
     * 递归对象对有注解的字段脱敏
     *
     * @param object 需脱敏对象
     * @throws Exception 异常
     */
    private void desensitizeObject(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        if (AbstractCollection.class.isAssignableFrom(clazz) && !AbstractMap.class.isAssignableFrom(clazz)) {
            for(Object obj : (Collection<?>)object){
                this.desensitizeDTO(obj);
            }
        } else {
            this.desensitizeDTO(object);
        }
    }

    /**
     * 针对对象中DTO脱敏
     *
     * @param object DTO 对象
     * @throws Exception 异常
     */
    private void desensitizeDTO(Object object) throws Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            this.desensitizeField(object, field);
        }
    }

    /**
     * 针对对象中字段脱敏
     *
     * @param data 脱敏数据对象
     * @param field 脱敏字段
     * @throws IllegalAccessException 非法访问异常
     */
    private void desensitizeField(Object data, Field field) throws Exception {
        // 注解为 Sensitive 对当前字段脱敏
        Desensitize desensitize = field.getAnnotation(Desensitize.class);
        field.setAccessible(true);
        Object obj = field.get(data);
        if (obj == null) {
            return;
        }
        if (desensitize != null) {
            field.set(data, Sensibler.getInstance().desensitize(desensitize.value(), obj.toString()));
        }

        // 注解为 Valid 对当前对象里面的字段递归脱敏
        Sensitive sensitive = field.getAnnotation(Sensitive.class);
        if (sensitive != null) {
            this.desensitizeObject(obj);
        }
    }

}
