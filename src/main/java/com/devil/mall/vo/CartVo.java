package com.devil.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/5/26
 * 购物车
 */

@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;

    private Boolean selectedAll;

    private BigDecimal CartTotalPrice;

    private Integer cartTotalQuantity;
}
