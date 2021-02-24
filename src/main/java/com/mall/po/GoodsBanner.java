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
@Table(name = "goods_banner")
public class GoodsBanner {

    /**
     * 商品轮播图集合id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关联的商品id
     */
    private Integer goodsId;

    /**
     * 轮播图标识
     */
    private String name;

    /**
     * 轮播图地址集合
     */
    @Column(columnDefinition = "TEXT")
    private String urls;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
