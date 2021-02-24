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
@Table(name = "goods_brand")
public class GoodsBrand {

    /**
     * 商品品牌id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品品牌名称
     */
    private String name;

    /**
     * 商品品牌介绍
     */
    private String introduction;

    /**
     * 商品品牌logo
     */
    private String logoUrl;

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
