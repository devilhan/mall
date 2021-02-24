package com.mall.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Hanyanjiao
 * @date 2021/2/24
 */

@Data
@Entity
@Table(name = "goods_category")
public class GoodsCategory {

    /**
     * 商品类别id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品类别名称
     */
    private String name;

    /**
     * 父类别id
     */
    private Integer parentId;

    /**
     * 排序号
     */
    private Integer sortNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
