package com.hesh.vo.user;

/**
 * Created by zhaoji on 2015/8/12.
 */
public enum PromoteResponseCode {
    /** 处理成功 */
    SUCCESS("SUCCESS", "成功"),
    /** 处理失败 */
    FAILURE("FAILURE", "失败"),
    /** 处理警告*/
    WARNING("WARNING", "警告"),
    /** 系统错误 */
    SYSTEM_ERROR("SYSTEM_ERROR", "系统错误"),
    /** 保存错误 */
    SAVE_ERROR("SYSTEM_ERROR", "保存错误"),
    /**参数错误*/
    PARAM_ERROR("PARAM_ERROR", "参数错误"),
    /**参数重复**/
    PARAM_REPEAT("PARAM_REPEAT","参数%s重复"),
    /**属性拷贝错误*/
    PROPERTY_COPY_ERROR("PROPERTY_COPY_ERROR", "属性拷贝错误"),
    /**skuList为空错误*/
    SKULIST_NULL_ERROR("SKULIST_NULL_ERROR", "skuList为空错误"),
    /**促销已结束*/
    PROMOTE_RUNOUT("COUPON_RUNOUT","促销已%s已结束"),
    /**参数必须填写**/
    PARAM_REQUIRED("PARAM_REQUIRED","参数%s不能为空"),
    /**参数值错误**/
    PARAM_VALUE_ERROR("PARAM_VALUE_ERROR","参数%s必须%s"),
    /**操作过于频繁**/
    OPER_FREQUENTLY("OPER_FREQUENTLY","操作过于频繁，请稍后重试"),
    /**
     * 回滚操作重复
     */
    EXE_REPEAT("101","回滚操作重复！"),
    /**
     * 操作数据库执行异常
     */
    DB_FAIL("500","由于操作服务器执行异常,回滚订单失败"),
    /**
     * 该订单不存在
     */
    ORDER_NOT_FIND("404","该订单不存在！"),

    /**
     * 该订单已存在
     */
    ORDER_IS_EXIST("506","该订单已存在！"),

    /**
     * 订单参数错误
     */
    ORDER_PARAM_ERROR("405","传递参数不合法！");

    private String code;
    private String msg;

    private PromoteResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
