package com.devil.mall.service;

import com.devil.mall.form.ShippingForm;
import com.devil.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @author Hanyanjiao
 * @date 2020/5/27
 */
public interface IShippingService {

    ResponseVo<Map<String,Integer>> add(Integer uid, ShippingForm shippingForm);

    ResponseVo delete(Integer uid,Integer shippingId);

    ResponseVo update(Integer uid, Integer shippingId,ShippingForm shippingForm);

    ResponseVo<PageInfo> list(Integer uid,Integer pageNum,Integer pageSize);
}
