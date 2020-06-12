package com.devil.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Hanyanjiao
 * @date 2020/5/25
 */

@Data
public class ProductVo {

    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;
}
