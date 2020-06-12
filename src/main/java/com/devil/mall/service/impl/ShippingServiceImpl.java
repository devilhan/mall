package com.devil.mall.service.impl;

import com.devil.mall.dao.ShippingMapper;
import com.devil.mall.enums.ResponseEnum;
import com.devil.mall.form.ShippingForm;
import com.devil.mall.pojo.Shipping;
import com.devil.mall.service.IShippingService;
import com.devil.mall.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hanyanjiao
 * @date 2020/5/27
 */
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVo<Map<String,Integer>> add(Integer uid, ShippingForm shippingForm) {
        Shipping shipping = new Shipping();
        Map<String,Integer> map = new HashMap<>();
        BeanUtils.copyProperties(shippingForm,shipping);
        shipping.setUserId(uid);

        int row = shippingMapper.insertSelective(shipping);
        if (row == 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        map.put("shppingId",shipping.getId());

        return ResponseVo.success(map);
    }

    @Override
    public ResponseVo delete(Integer uid, Integer shippingId) {
        int row = shippingMapper.deleteByIdAndUid(uid,shippingId);
        if(row == 0){
            return ResponseVo.error(ResponseEnum.DELETE_SHIPPING_FAIL);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo update(Integer uid, Integer shippingId, ShippingForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form,shipping);
        shipping.setUserId(uid);
        shipping.setId(shippingId);
        int row = shippingMapper.updateByPrimaryKeySelective(shipping);

        if(row == 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {

        List<Shipping> shippings = shippingMapper.selectByUid(uid);

        PageHelper.startPage(pageNum,pageSize);
        PageInfo pageInfo = new PageInfo(shippings);
        return ResponseVo.success(pageInfo);
    }
}
