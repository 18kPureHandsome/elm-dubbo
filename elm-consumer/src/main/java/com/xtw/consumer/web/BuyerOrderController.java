package com.xtw.consumer.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xtw.api.converter.OrderForm2OrderDTO;
import com.xtw.api.dto.OrderDTO;
import com.xtw.api.enums.ExceptionEnums;
import com.xtw.api.exception.MyException;
import com.xtw.api.form.OrderForm;
import com.xtw.api.service.OrderService;
import com.xtw.api.util.ResultVoUtil;
import com.xtw.api.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : tianwen.xiao
 * @ClassName : BuyerOrderController
 * @Description : 买家订单controller
 * @date : created in 2019/3/7 9:55 AM
 * @Version : 1.0
 */
@Slf4j
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Reference
    private OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public ResultVo<Map<String,String>> create (@Valid OrderForm orderForm,
                                               BindingResult bindingResult) throws Exception{
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确 orderForm = {}",orderForm);
            throw new MyException(ExceptionEnums.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTO.converter(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空 ");
            throw new MyException(ExceptionEnums.CART_EMPTY);
        }

        OrderDTO orderResult = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>(1);
        map.put("orderid",orderResult.getOrderId());
        return ResultVoUtil.SuccessVo(map);

    }

    @GetMapping("/testcreate")
    public ResultVo<Map<String,String>> testcreate () throws Exception{
        OrderForm orderForm = new OrderForm();
        orderForm.setName("张三");
        orderForm.setPhone("18868822111");
        orderForm.setAddress("慕课网总部");
        orderForm.setOpenid("ew3euwhd7sjw9diwkq");
        orderForm.setItems("[{productId: \"111\",productQuantity: 1}]");
        OrderDTO orderDTO = OrderForm2OrderDTO.converter(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空 ");
            throw new MyException(ExceptionEnums.CART_EMPTY);
        }

        OrderDTO orderResult = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>(1);
        map.put("orderid",orderResult.getOrderId());
        return ResultVoUtil.SuccessVo(map);

    }


    /**
     * 订单列表
     */
    @GetMapping("/list")
    public ResultVo<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size) throws Exception{
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw new MyException(ExceptionEnums.PARAM_ERROR);
        }

        List<OrderDTO> orderDTOList = orderService.findList(openid);
        return ResultVoUtil.SuccessVo(orderDTOList);
    }

    /**
     * 订单详情
     */
    @GetMapping("/detail")
    public ResultVo<OrderDTO> detail(@RequestParam("orderid") String orderid,
                                     @RequestParam("openid")String openid) throws Exception{
        OrderDTO orderDTO = orderService.checkOrderOwner(orderid,openid);
        return ResultVoUtil.SuccessVo(orderDTO);
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel")
    public ResultVo cancel(@RequestParam("orderid") String orderid,
                           @RequestParam("openid")String openid) throws Exception{
        OrderDTO orderDTO = orderService.checkOrderOwner(orderid,openid);
        orderService.cancel(orderDTO);
        return ResultVoUtil.SuccessVo(null);
    }
}
