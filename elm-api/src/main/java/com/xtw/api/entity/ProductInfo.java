package com.xtw.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author : tianwen.xiao
 * @ClassName : ProductInfo
 * @Description : 商品详情entity
 * @date : created in 2019/3/4 10:08 AM
 * @Version : 1.0
 */
@Data
public class ProductInfo implements Serializable {
    private static final long serialVersionUID = -6112190453294786350L;
    /** id */
    private String productId;

    /** 商品名称*/
    private String productName;

    /** 商品价格 */
    private BigDecimal productPrice;

    /** 商品库存 */
    private Integer productStock;

    /** 商品描述 */
    private String productDescription;

    /** 商品图片 */
    private String productIcon;

    /** 商品状态,0正常1下架 */
    private Byte productStatus;

    /** 类目编号 */
    private Integer categoryType;

    /** 创建时间*/
    private Date createTime;

    /** 修改时间*/
    private Date updateTime;


}