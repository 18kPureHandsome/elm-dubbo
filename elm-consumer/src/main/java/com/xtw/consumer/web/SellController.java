package com.xtw.consumer.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xtw.api.entity.ProductCategory;
import com.xtw.api.entity.ProductInfo;
import com.xtw.api.service.ProductCategoryService;
import com.xtw.api.service.ProductInfoService;
import com.xtw.api.util.ResultVoUtil;
import com.xtw.api.vo.ProductCategoryVo;
import com.xtw.api.vo.ProductInfoVo;
import com.xtw.api.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : tianwen.xiao
 * @ClassName : SellController
 * @Description : 买家端controller
 * @date : created in 2019/3/4 10:08 AM
 * @Version : 1.0
 */
@RestController()
@RequestMapping("/buyer")
public class SellController {

    @Reference
    private ProductInfoService productInfoService;

    @Reference
    private ProductCategoryService productCategoryService;

    @GetMapping("/product/list")
    public ResultVo productList() throws Exception{
        //1.查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        //2.查询类目
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼装
        List<ProductCategoryVo> categoryVos = new ArrayList<>();

        for (ProductCategory productCategory : categoryList) {
            ProductCategoryVo productCategoryVo = new ProductCategoryVo();
            productCategoryVo.setCategoryName(productCategory.getCategoryName());
            productCategoryVo.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVo> productInfoVos = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVos.add(productInfoVo);
                }
            }
            productCategoryVo.setProductInfoVoList(productInfoVos);
            categoryVos.add(productCategoryVo);
        }

        return ResultVoUtil.SuccessVo(categoryVos);
    }
}
