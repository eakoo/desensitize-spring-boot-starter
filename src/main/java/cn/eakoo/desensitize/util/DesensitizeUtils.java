package cn.eakoo.desensitize.util;

/**
 * 数据脱敏工具类
 *
 * @author rui.zhou
 * @date 2021/10/19 17:59
 **/
public class DesensitizeUtils {

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final int ELEVEN = 11;

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

        if (address.length() > THREE && address.length() <= ELEVEN) {
            return address.substring(0, 3) + DesensitizeUtils.appendAsterisk(address.length() - 3);
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
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }

}
