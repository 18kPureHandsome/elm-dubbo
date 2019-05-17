package com.xtw.api.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author : tianwen.xiao
 * @ClassName : OrderForm
 * @Description : 订单form
 * @date : created in 2019/3/7 9:59 AM
 * @Version : 1.0
 */
@Data
public class OrderForm {

    /**
     * 买家姓名
     */
    @NotEmpty(message = "买家姓名必填")
    private String name;

    /**
     * 买家电话
     */
    @NotEmpty(message = "买家电话必填")
    private String phone;

    /**
     * 买家地址
     */
    @NotEmpty(message = "买家地址必填")
    private String address;

    /**
     * 买家openid
     */
    @NotEmpty(message = "买家openid必填")
    private String openid;


    /**
     * 购物车
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;
}
