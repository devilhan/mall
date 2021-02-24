package com.mall.po;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author Hanyanjiao
 * @date 2021/2/24
 */

@Data
@Entity
@Table(name = "goods")
public class Goods {

    /**
     * 商品id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品默认图片地址
     */
    private String thumbUrl;

    /**
     * 商品默认价格
     */
    private BigDecimal price;

    /**
     * 商品类别id
     */
    private Integer categoryId;

    /**
     * 商品品牌id
     */
    private Integer brandId;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品介绍
     */
    private String introduction;

    /**
     * 预警库存
     */
    private Integer warningStock;

    /**
     * 单次最大购买量
     */
    private Integer maxBuy;

    /**
     * 商品单位
     */
    private String unit;

    /**
     * 商品概述类型 0：图片 1：网页
     */
    private Integer sketchType;

    /**
     * 商品概述
     */
    @Column(columnDefinition = "TEXT")
    private String sketch;

    /**
     * 商品参数
     */
    @Column(columnDefinition = "TEXT")
    private String specs;

    /**
     * 规格类型 0：单规格 1：多规格
     */
    private Integer skuType;

    /**
     * 状态 0：上架 1：下架
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
