package com.xtw.api.enums;

import lombok.Getter;

/**
 * @author : tianwen.xiao
 * @date : created in 2019/3/6 2:10 PM
 */
@Getter
public enum OrderStatusEnums {

    NEW(0,"新订单"),

    FINISH(1,"订单完结"),

    CANCEL(2,"订单取消"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
