package cn.eakoo.desensitize;

import java.util.function.UnaryOperator;

/**
 * 脱敏的数据脱敏策略
 *
 * @author rui.zhou
 * @date 2021/10/18 10:31
 **/
public interface Sensible {

    /**
     * 脱敏
     *
     * @param type 脱敏策略
     * @param value 待脱敏值
     * @return String
     */
    String desensitize(String type, String value);

    /**
     * 添加脱敏策略
     *
     * @param type 脱敏策略
     * @param function 脱敏实现
     * @return SensitiveStrategy
     */
    Sensible addDesensitizeStrategy(String type, UnaryOperator<String> function);

}
