package com.xtw.provider.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.xtw.api.util.KeyUtil;
import com.xtw.api.dto.CartDTO;
import com.xtw.api.dto.OrderDTO;
import com.xtw.api.entity.OrderDetail;
import com.xtw.api.entity.OrderMaster;
import com.xtw.api.entity.ProductInfo;
import com.xtw.api.enums.ExceptionEnums;
import com.xtw.api.enums.OrderStatusEnums;
import com.xtw.api.enums.PayStatusEnums;
import com.xtw.api.exception.MyException;
import com.xtw.api.mapper.OrderDetailMapper;
import com.xtw.api.mapper.OrderMasterMapper;
import com.xtw.api.service.OrderService;
import com.xtw.api.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : tianwen.xiao
 * @ClassName : OrderServiceImpl
 * @Description :
 * @date : created in 2019/3/6 2:54 PM
 * @Version : 1.0
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Reference
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public OrderDTO create(OrderDTO orderdto) throws Exception{
        String orderid = KeyUtil.getUniquekey();
        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);

        //1.查询商品（数量，价格）
        for (OrderDetail orderDetail : orderdto.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                log.error("【创建订单】请求商品不存在 orderdto = {}",orderdto);
                throw new MyException(ExceptionEnums.PRODUCT_NOT_EXIST);
            }

            //2.计算订单总价
            orderAmout = productInfo.getProductPrice()
                        .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                        .add(orderAmout);

            //订单详情入库
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setDetailId(KeyUtil.getUniquekey());
            orderDetail.setOrderId(orderid);
            orderDetailMapper.insert(orderDetail);
        }


        //3.写入订单数据库（order_master,order_detail）

        OrderMaster orderMaster = new OrderMaster();
        orderdto.setOrderId(orderid);
        BeanUtils.copyProperties(orderdto,orderMaster);
        orderMaster.setOrderAmount(orderAmout);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());
        orderMasterMapper.insert(orderMaster);

        //4.扣库存
        List<CartDTO> cartDTOList = orderdto.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        return orderdto;
    }

    @Override
    public OrderDTO findOne(String orderid) throws Exception{
        OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderid);
        if(orderMaster == null) {
            throw new MyException(ExceptionEnums.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailMapper.findByOrderId(orderMaster.getOrderId());
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new MyException(ExceptionEnums.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public List<OrderDTO> findList(String openid) {
        List<OrderMaster> orderMasterList = orderMasterMapper.findByBuyerOpenId(openid);

        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(OrderMaster orderMaster : orderMasterList){
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(orderMaster,orderDTO);
            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO cancel(OrderDTO orderdto) throws Exception{
        OrderMaster orderMaster = new OrderMaster();
        //1.判断订单状态
        if(!orderdto.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("[取消订单]  订单状态不正确，orderid = {}，orderStatus={}",orderdto.getOrderId(),orderdto.getOrderStatus());
            throw new MyException(ExceptionEnums.ORDER_STATUS_ERROR);
        }

        //2.修改订单状态
        orderdto.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        BeanUtils.copyProperties(orderdto,orderMaster);
        int result = orderMasterMapper.updateByPrimaryKey(orderMaster);
        if(result <=0){
            log.error("[取消订单]  更新失败，orderMaster = {}",orderMaster);
            throw new MyException(ExceptionEnums.ORDER_UPDATE_FAIL);
        }

        //3.返还库存
        if(CollectionUtils.isEmpty(orderdto.getOrderDetailList())){
            log.error("[取消订单]  订单中无商品详情，orderdto = {}",orderdto);
            throw new MyException(ExceptionEnums.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderdto.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //4.如果已支付，需要退款
        if(orderdto.getPayStatus().equals(PayStatusEnums.SUCCESS.getCode())){
            //TODO
        }

        return orderdto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO finish(OrderDTO orderdto) throws Exception{
        //1.判断订单状态
        if(!orderdto.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("[完结订单]  订单状态不正确，orderid = {}，orderStatus={}",orderdto.getOrderId(),orderdto.getOrderStatus());
            throw new MyException(ExceptionEnums.ORDER_STATUS_ERROR);
        }
        //2.修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderdto.setOrderStatus(OrderStatusEnums.FINISH.getCode());
        BeanUtils.copyProperties(orderdto,orderMaster);
        int result = orderMasterMapper.updateByPrimaryKey(orderMaster);
        if(result <=0){
            log.error("[完结订单]  更新失败，orderMaster = {}",orderMaster);
            throw new MyException(ExceptionEnums.ORDER_UPDATE_FAIL);
        }
        return orderdto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO paid(OrderDTO orderdto) throws Exception{

        //1.判断订单状态
        if(!orderdto.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("[支付订单]  订单状态不正确，orderid = {}，orderStatus={}",orderdto.getOrderId(),orderdto.getOrderStatus());
            throw new MyException(ExceptionEnums.ORDER_STATUS_ERROR);
        }
        //2.判断支付状态
        if(orderdto.getPayStatus().equals(PayStatusEnums.WAIT.getCode())){
            log.error("[支付订单] 订单支付状态不正确，OrderDTO = {}",orderdto);
            throw new MyException(ExceptionEnums.ORDER_PAY_STATUS_ERROR);
        }

        //3.修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderdto.setPayStatus(PayStatusEnums.SUCCESS.getCode());
        BeanUtils.copyProperties(orderdto,orderMaster);
        int result = orderMasterMapper.updateByPrimaryKey(orderMaster);
        if(result <=0){
            log.error("[支付订单]  更新失败，orderMaster = {}",orderMaster);
            throw new MyException(ExceptionEnums.ORDER_UPDATE_FAIL);
        }
        return orderdto;
    }

    @Override
    public OrderDTO checkOrderOwner(String orderid, String openid) throws Exception {
        OrderDTO orderDTO = this.findOne(orderid);
        if(orderDTO == null){
            log.error("[校验当前用户订单]  订单为空，orderid = {},openid = {}",orderid,openid);
            throw new MyException(ExceptionEnums.ORDER_NOT_EXIST);
        }
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[校验当前用户订单]  订单openid不一致，orderid = {},openid = {}",orderid,openid);
            throw new MyException(ExceptionEnums.ORDER_NOT_EXIST);
        }
        return orderDTO;
    }
}
