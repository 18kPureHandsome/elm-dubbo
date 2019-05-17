package com.xtw.api.enums;

import lombok.Getter;

/**
 * @author : tianwen.xiao
 * @date : created in 2019/3/6 2:16 PM
 */
@Getter
public enum PayStatusEnums {

    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功"),

    ;


    private Integer code;

    private String message;

    PayStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
