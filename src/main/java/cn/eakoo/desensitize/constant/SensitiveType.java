package cn.eakoo.desensitize.constant;

/**
 * @author rui.zhou
 * @date 2021/10/17 18:33
 **/
public class SensitiveType {

    /**
     * 手机号码
     */
    public static final String MOBILE = "mobile";

    /**
     * 身份证
     */
    public static final String ID_CARD = "idCard";

    /**
     * 邮件
     */
    public static final String EMAIL = "email";

    /**
     * 银行卡
     */
    public static final String BANK_CARD = "bankCard";

    /**
     * 地址
     */
    public static final String ADDRESS = "address";

    /**
     * 中文名
     */
    public static final String CHINESE_NAME = "chineseName";

    private SensitiveType() {
    }
}
