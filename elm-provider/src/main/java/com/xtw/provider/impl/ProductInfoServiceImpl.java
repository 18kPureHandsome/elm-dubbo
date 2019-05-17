package com.xtw.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xtw.api.dto.CartDTO;
import com.xtw.api.entity.ProductInfo;
import com.xtw.api.enums.ExceptionEnums;
import com.xtw.api.enums.ProductStatusEnum;
import com.xtw.api.exception.MyException;
import com.xtw.api.mapper.ProductInfoMapper;
import com.xtw.api.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : tianwen.xiao
 * @ClassName : ProductInfoServiceImpl
 * @Description :
 * @date : created in 2019/3/4 11:46 AM
 * @Version : 1.0
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public int deleteByPrimaryKey(String productId) {
        return productInfoMapper.deleteByPrimaryKey(productId);
    }

    @Override
    public int insert(ProductInfo record) {
        return productInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(ProductInfo record) {
        return productInfoMapper.insertSelective(record);
    }

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoMapper.selectByPrimaryKey(productId);
    }

    @Override
    public int updateByPrimaryKeySelective(ProductInfo record) {
        return productInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ProductInfo record) {
        return productInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    @Cacheable(cacheNames = "productlist" , key="123")
    public List<ProductInfo> findUpAll() {
        return productInfoMapper.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    @CacheEvict(value = "productlist" , key="123")
    public void increaseStock(List<CartDTO> cartDTOList) throws Exception{
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(cartDTO.getProductId());
            if(productInfo == null){
                throw new MyException(ExceptionEnums.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();

            productInfo.setProductStock(result);
            productInfoMapper.updateByPrimaryKey(productInfo);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    @CacheEvict(value = "productlist" , key="123")
    public void decreaseStock(List<CartDTO> cartDTOList) throws Exception{
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(cartDTO.getProductId());
            if(productInfo == null){
                throw new MyException(ExceptionEnums.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result < 0 ){
                throw new MyException(ExceptionEnums.PRODUCT_STOCK_LOW);
            }
            productInfo.setProductStock(result);
            productInfoMapper.updateByPrimaryKey(productInfo);
        }
    }
}
