package com.xtw.api.service;

import com.xtw.api.entity.ProductCategory;

import java.util.List;

/**
 * @author : tianwen.xiao
 * @date : created in 2019/3/2 1:11 AM
 */
public interface ProductCategoryService {

    int deleteByPrimaryKey(Integer categoryId);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
