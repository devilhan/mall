package com.mall.po;

import com.google.gson.JsonObject;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hanyanjiao
 * @date 2021/2/24
 */

@Data
@Entity
@Table(name = "goods_sku")
public class GoodsSku {

    /**
     * 商品规格id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 商品属性json对象
     */
    private JsonObject goodsAttr;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 进价
     */
    private BigDecimal costPrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 轮播图集合id
     */
    private Integer bannersId;

    /**
     * 规格排序号
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
