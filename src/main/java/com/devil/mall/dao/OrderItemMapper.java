package com.devil.mall.dao;

import com.devil.mall.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
* Created by ESC han on 2020/05/14
*/
public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    int batchInsert(@Param("orderItems") List<OrderItem> orderItems);

    List<OrderItem>selectByOrderNoSet(@Param("orderNoSet") Set<Long> orderNoSet);

}