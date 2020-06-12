package com.devil.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Hanyanjiao
 * @date 2020/5/26
 */

@Data
@AllArgsConstructor
public class CartProductVo {

    private Integer productId;

    //购买数量
    private Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private Integer productStatus;

    private BigDecimal productPrice;

    //总价 数量*单价
    private BigDecimal productTotalPrice;

    private Integer productStock;

    //商品是否选中
    private Boolean productSelected;
}
