package com.xtw.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xtw.api.entity.ProductCategory;
import com.xtw.api.mapper.ProductCategoryMapper;
import com.xtw.api.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author : tianwen.xiao
 * @date : created in 2019/3/2 1:13 AM
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public int deleteByPrimaryKey(Integer categoryId) {
        return productCategoryMapper.deleteByPrimaryKey(categoryId);
    }

    @Override
    public int insert(ProductCategory record) {
        return productCategoryMapper.insert(record);
    }

    @Override
    public int insertSelective(ProductCategory record) {
        return productCategoryMapper.insertSelective(record);
    }

    @Override
    public ProductCategory selectByPrimaryKey(Integer categoryId) {
        return productCategoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public int updateByPrimaryKeySelective(ProductCategory record) {
        return productCategoryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ProductCategory record) {
        return productCategoryMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryMapper.findByCategoryTypeIn(categoryTypeList);
    }
}
