package com.devil.mall.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Hanyanjiao
 * @date 2020/5/26
 * 添加商品
 */

@Data
@AllArgsConstructor
public class CartAddForm {

    @NotNull
    private Integer productId;

    private Boolean selected = true;
}
