package com.xtw.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xtw.api.entity.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author : tianwen.xiao
 * @ClassName : OrderDTO
 * @Description :
 * @date : created in 2019/3/6 2:42 PM
 * @Version : 1.0
 */
@Data
public class OrderDTO {
    /** 订单id*/
    private String orderId;

    /** 买家名字*/
    private String buyerName;

    /** 买家电话*/
    private String buyerPhone;

    /** 买家地址*/
    private String buyerAddress;

    /** 买家openid*/
    private String buyerOpenid;

    /** 订单总金额*/
    private BigDecimal orderAmount;

    /** 订单状态*/
    private Integer orderStatus;

    /** 支付状态*/
    private Integer payStatus;

    /** 创建时间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /** 修改时间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    List<OrderDetail> orderDetailList;
}
