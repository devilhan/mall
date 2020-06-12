package com.devil.mall.service;

import com.devil.mall.vo.OrderVo;
import com.devil.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

/**
 * @author Hanyanjiao
 * @date 2020/5/28
 */
public interface IOrderService {

    ResponseVo<OrderVo> create(Integer uid,Integer shippingId);

    ResponseVo<PageInfo> list(Integer uid,Integer pageNum,Integer pageSize);

    ResponseVo<OrderVo> detail(Integer uid,Long orderNo);

    ResponseVo cancel(Integer uid,Long orderNo);

    void paid(Long orderNo);
}
