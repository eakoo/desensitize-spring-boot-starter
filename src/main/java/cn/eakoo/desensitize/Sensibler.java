package cn.eakoo.desensitize;

import cn.eakoo.desensitize.constant.SensitiveType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * 敏感数据脱敏实现
 *
 * @author rui.zhou
 * @date 2021/10/18 10:33
 **/
public class Sensibler implements Sensible{

    private static final Logger logger = LoggerFactory.getLogger(Sensibler.class);

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final int ELEVEN = 11;

    private static final Map<String, UnaryOperator<String>> STRATEGY_MAP = new HashMap<>(16);

    static {
        STRATEGY_MAP.put(SensitiveType.ADDRESS, Sensibler::desensitizedAddress);
        STRATEGY_MAP.put(SensitiveType.BANK_CARD, Sensibler::desensitizedBankCard);
        STRATEGY_MAP.put(SensitiveType.CHINESE_NAME, Sensibler::desensitizedName);
        STRATEGY_MAP.put(SensitiveType.EMAIL, Sensibler::desensitizedEmail);
        STRATEGY_MAP.put(SensitiveType.ID_CARD, Sensibler::desensitizedIdCard);
        STRATEGY_MAP.put(SensitiveType.MOBILE, Sensibler::desensitizedPhone);
    }



    private static class SingletonHolder {
        private static final Sensibler INSTANCE = new Sensibler();
    }

    private Sensibler (){ }

    public static Sensibler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String desensitize(String type, String value) {
        if (value == null || type == null) {
            return null;
        }
        UnaryOperator<String> unaryOperator = STRATEGY_MAP.get(type);
        return unaryOperator == null ? null : unaryOperator.apply(value);
    }

    @Override
    public Sensibler addDesensitizeStrategy(String type, UnaryOperator<String> function) {
        UnaryOperator<String> stringUnaryOperator = STRATEGY_MAP.get(type);
        if (stringUnaryOperator != null){
            logger.warn("{} Desensitize Strategy Covered", type);
        }
        STRATEGY_MAP.put(type, function);
        return this;
    }

    /**
     * 中文名字脱敏
     *
     * @param name 中文名字
     * @return String
     */
    private static String desensitizedName(String name){
        if (name == null) {
            return null;
        }
        if (name.length() == TWO) {
            return name.charAt(0) + "*";
        }
        if (name.length() >= THREE) {
            return name.charAt(0) + "*" + name.substring(name.length() - 1);
        }
        return name;
    }

    /**
     * 手机号码脱敏
     *
     * @param phone 电话号码
     * @return String
     */
    private static String desensitizedPhone(String phone){
        if (phone == null) {
            return null;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 身份证脱敏
     *
     * @param idCard 身份证
     * @return String
     */
    private static String desensitizedIdCard(String idCard){
        if (idCard == null) {
            return null;
        }
        return idCard.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
    }

    /**
     * 银行卡脱敏
     *
     * @param bankCard 银行卡
     * @return String
     */
    private static String desensitizedBankCard(String bankCard){
        if (bankCard == null) {
            return null;
        }
        return bankCard.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
    }

    /**
     * 邮件脱敏
     *
     * @param email 邮件
     * @return String
     */
    private static String desensitizedEmail(String email){
        if (email == null) {
            return null;
        }
        return email.replaceAll("(^\\w)[^@]*(@.*$)", "$1****$2");
    }

    /**
     * 地址脱敏
     * 脱敏规则: 从第4位开始隐藏, 隐藏8位
     *
     * @param address 地址
     * @return String
     */
    private static String desensitizedAddress(String address){
        if (address == null) {
            return null;
        }
        if (address.length() <= THREE) {
            return address;
        }

        if (address.length() <= ELEVEN) {
            return address.substring(0, 3) + Sensibler.appendAsterisk(address.length() - 3);
        }
        return address.substring(0, 3) + "********" + address.substring(11);
    }

    /**
     * 返回 N个星号
     *
     * @param size 个数
     * @return String
     */
    private static String appendAsterisk(int size){
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }

}
