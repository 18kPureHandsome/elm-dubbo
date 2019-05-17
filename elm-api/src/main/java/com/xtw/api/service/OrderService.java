package com.xtw.api.service;

import com.xtw.api.dto.OrderDTO;

import java.util.List;

/**
 * @author : tianwen.xiao
 * @date : created in 2019/3/6 2:37 PM
 */
public interface OrderService {

    /**
     * 创建订单
     */
    OrderDTO create(OrderDTO orderdto) throws Exception;

    /**
     * 查询单个订单
     */
    OrderDTO findOne(String orderid) throws Exception;

    /**
     * 查询订单列表
     */
    List<OrderDTO> findList(String openid);

    /**
     * 取消订单
     */
    OrderDTO cancel(OrderDTO orderdto) throws Exception;

    /**
     * 完结订单
     */
    OrderDTO finish(OrderDTO orderdto) throws Exception;

    /**
     * 支付订单
     */
    OrderDTO paid(OrderDTO orderdto) throws Exception;

    /**
     * 校验订单是否属于当前用户
     * @param openid 买家openid
     * @param orderid 订单id
     */
    OrderDTO checkOrderOwner(String orderid, String openid) throws Exception;
}
