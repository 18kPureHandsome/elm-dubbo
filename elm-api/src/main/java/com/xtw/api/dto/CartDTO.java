package com.xtw.api.dto;

import lombok.Data;

/**
 * @author : tianwen.xiao
 * @ClassName : CartDTO
 * @Description : 购物车
 * @date : created in 2019/3/6 3:40 PM
 * @Version : 1.0
 */
@Data
public class CartDTO {

    /** 商品id*/
    private String productId;

    /** 商品数量*/
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
