package com.devil.mall.dao;

import com.devil.mall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
* Created by ESC han on 2020/05/27
*/
public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByIdAndUid(@Param("uid") Integer uid,@Param("shippingId") Integer shippingId);

    List<Shipping> selectByUid(Integer uid);

    Shipping selectByUidAndId(@Param("uid") Integer uid,@Param("shippingId") Integer shippingId);

    List<Shipping> selectByIdSet(@Param("idSet")Set<Integer> idSet);
}