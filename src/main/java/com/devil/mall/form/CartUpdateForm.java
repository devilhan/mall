package com.devil.mall.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hanyanjiao
 * @date 2020/5/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateForm {

    private Integer quantity;

    private Boolean selected;
}
