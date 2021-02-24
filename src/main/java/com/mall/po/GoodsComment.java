package com.mall.po;

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
@Table(name = "goods_comment")
public class GoodsComment {

    /**
     * 商品评价id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关联的订单号
     */
    private String orderNo;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 分数
     */
    private BigDecimal score;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 分享的图片集合
     */
    private String photoUrls;

    /**
     * 点赞数量
     */
    private Integer likes;

    /**
     * 显示状态 0：正常 1：隐藏
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
