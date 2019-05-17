package com.xtw.api.service;

import com.xtw.api.dto.CartDTO;
import com.xtw.api.entity.ProductInfo;

import java.util.List;

/**
 * @author : tianwen.xiao
 * @ClassName : ProductInfoService
 * @Description :
 * @date : created in 2019/3/4 11:44 AM
 * @Version : 1.0
 */
public interface ProductInfoService {
    int deleteByPrimaryKey(String productId);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo findOne(String productId);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    /**
     * 查询所有上架的商品
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 加库存
     * @param cartDTOList
     */
    void increaseStock(List<CartDTO> cartDTOList) throws Exception;

    /**
     * 减库存
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList) throws Exception;

}
