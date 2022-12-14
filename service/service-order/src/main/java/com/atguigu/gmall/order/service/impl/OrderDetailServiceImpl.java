package com.atguigu.gmall.order.service.impl;

import com.atguigu.gmall.model.order.OrderDetail;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.order.service.OrderDetailService;
import com.atguigu.gmall.order.mapper.OrderDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Feng
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-09-12 00:33:23
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Override
    public List<OrderDetail> getOrderDetails(Long orderId, Long userId) {
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId,orderId);
        queryWrapper.eq(OrderDetail::getUserId,userId);
        return orderDetailMapper.selectList(queryWrapper);
    }
}




