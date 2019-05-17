package com.xtw.api.enums;

import lombok.Getter;

/**
 * @author : tianwen.xiao
 * @ClassName : ExceptionEnums
 * @Description :
 * @date : created in 2019/3/6 10:17 AM
 * @Version : 1.0
 */
@Getter
public enum ExceptionEnums {

    PRODUCT_NOT_EXIST(10,"请求商品不存在"),

    EXCEPTION_TIMEOUT(1,"请求超时"),

    PRODUCT_STOCK_LOW(11,"商品库存不足"),

    ORDER_NOT_EXIST(12,"订单不存在"),

    ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),

    ORDER_STATUS_ERROR(14,"订单状态不正确"),

    ORDER_UPDATE_FAIL(15,"订单更新失败"),

    ORDER_DETAIL_EMPTY(16,"订单详情为空"),

    ORDER_PAY_STATUS_ERROR(17,"订单支付状态不正确"),

    PARAM_ERROR(18,"参数不正确"),

    CART_EMPTY(19,"购物车不能为空"),
    ;

    private Integer code;

    private String message;

    ExceptionEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
