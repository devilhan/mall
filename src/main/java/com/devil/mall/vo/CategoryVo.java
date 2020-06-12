package com.devil.mall.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/5/22
 */

@Data
public class CategoryVo {
    private Integer id;

    private Integer parentId;

    private String name;

    private Integer sortOrder;

    //递归
    private List<CategoryVo> subCategories;

}
