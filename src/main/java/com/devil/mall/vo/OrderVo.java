package com.devil.mall.vo;

import com.devil.mall.pojo.Shipping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/5/28
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

    private Long orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private Date paymentTime;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    private List<OrderItemVo> orderItemVos;

    private Integer shippingId;

    private Shipping shippingVo;

}
