package com.devil.mall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hanyanjiao
 * @date 2020/5/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    private Integer productId;

    private Integer quantity;

    private Boolean selected;
}
