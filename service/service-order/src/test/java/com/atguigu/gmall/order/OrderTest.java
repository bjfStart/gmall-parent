package com.atguigu.gmall.order;

import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.order.mapper.OrderInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author feng
 * @create 2022-09-12 0:37
 */
@SpringBootTest
public class OrderTest {
    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Test
    public void testAll(){
        List<OrderInfo> infos = orderInfoMapper.selectList(null);
        for (OrderInfo info : infos) {
            System.out.println(info.getId() + "-->" + info.getTotalAmount() + "--> user:" +info.getUserId());
        }
    }

    @Test
    public void testQuery(){
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",2L);
        List<OrderInfo> infos = orderInfoMapper.selectList(wrapper);
        for (OrderInfo info : infos) {
            System.out.println(info.getTotalAmount());
        }
        System.out.println("========================|");
        List<OrderInfo> infos2 = orderInfoMapper.selectList(wrapper);
        for (OrderInfo info : infos2) {
            System.out.println(info.getTotalAmount());
        }
    }

    @Test
    public void testSplit(){
        OrderInfo info = new OrderInfo();
        info.setTotalAmount(new BigDecimal("777"));
        info.setUserId(1L);
        orderInfoMapper.insert(info);
        System.out.println("1号用户订单插入完成.....去1库1表");

        OrderInfo info2 = new OrderInfo();
        info2.setTotalAmount(new BigDecimal("666"));
        info2.setUserId(2L);
        orderInfoMapper.insert(info2);
        System.out.println("2号用户订单插入完成.....去 0库2表");

    }

    @Test
    void hhh(){
        OrderInfo orderInfo = orderInfoMapper.selectById(205L);
        System.out.println(orderInfo);
    }
}
