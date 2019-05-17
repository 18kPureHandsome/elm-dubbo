package com.xtw.api.enums;

import lombok.Getter;

/**
 * @author : tianwen.xiao
 * @ClassName : ProductStatusEnum
 * @Description : 商品状态enum
 * @date : created in 2019/3/4 10:08 AM
 * @Version : 1.0
 */
@Getter
public enum ProductStatusEnum {

    UP(0,"上架"),
    DOWN(1,"下架"),
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
