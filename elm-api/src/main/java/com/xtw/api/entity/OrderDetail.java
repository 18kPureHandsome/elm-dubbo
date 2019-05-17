package com.xtw.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = -6317604670524234297L;
    /** detailId*/
    private String detailId;

    /** 订单id*/
    private String orderId;

    /** 商品id*/
    private String productId;

    /** 商品名称*/
    private String productName;

    /** 商品单价*/
    private BigDecimal productPrice;

    /** 商品数量*/
    private Integer productQuantity;

    /** 商品图片*/
    private String productIcon;

    /** 创建时间*/
    private Date createTime;

    /** 修改时间*/
    private Date updateTime;


}