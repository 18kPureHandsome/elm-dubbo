package com.xtw.api.mapper;

import com.xtw.api.entity.ProductCategory;

import java.util.List;
/**
 * @author : tianwen.xiao
 * @ClassName : ProductCategoryMapper
 * @Description : 商品类目mapper
 * @date : created in 2019/3/4 10:08 AM
 * @Version : 1.0
 */

public interface ProductCategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}