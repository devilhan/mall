package com.devil.mall.form;

import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * @author Hanyanjiao
 * @date 2020/6/2
 */

@Getter
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;
}
