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
@Table(name = "goods_attr_value")
public class GoodsValue {

    /**
     * 商品类别属性值id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关联的商品类别属性名id
     */
    private Integer attrId;

    /**
     * 商品类别属性值
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
