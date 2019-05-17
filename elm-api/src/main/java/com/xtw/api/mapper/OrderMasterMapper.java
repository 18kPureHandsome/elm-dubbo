package com.xtw.api.mapper;

import com.xtw.api.entity.OrderMaster;

import java.util.List;

public interface OrderMasterMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderMaster record);

    int insertSelective(OrderMaster record);

    OrderMaster selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderMaster record);

    int updateByPrimaryKey(OrderMaster record);

    List<OrderMaster> findByBuyerOpenId(String openid);
}