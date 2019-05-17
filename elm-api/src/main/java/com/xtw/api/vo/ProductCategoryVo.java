package com.xtw.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiaotianwen on 2019/3/3.
 * 商品类别vo
 */
@Data
public class ProductCategoryVo implements Serializable {

    private static final long serialVersionUID = -5965353043336717958L;
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVoList;
}
