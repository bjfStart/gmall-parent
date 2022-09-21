package com.atguigu.gmall.order.service;

import com.atguigu.gmall.model.enums.ProcessStatus;
import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.model.vo.order.OrderSubmitVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Feng
* @description 针对表【order_info(订单表 订单表)】的数据库操作Service
* @createDate 2022-09-12 00:33:23
*/
public interface OrderInfoService extends IService<OrderInfo> {


    Long saveOrder(OrderSubmitVo submitVo, String tradeNo);

    /**
     * 关闭订单状态
     * @param orderId
     * @param userId
     * @param whileChange 想要改变的状态
     * @param expected
     */
    void changeOrderStatus(Long orderId, Long userId, ProcessStatus whileChange, List<ProcessStatus> expected);

    /**
     * 根据对外交易号和用户id获取订单信息
     * @param outTradeNo
     * @param userId
     * @return
     */
    OrderInfo getOrderInfoByOutTradeNoAndUserId(String outTradeNo, Long userId) ;

    /**
     * 查询订单数据
     * @param orderId
     * @param userId
     * @return
     */
    OrderInfo getOrderInfoByOrderIdAndUserId(Long orderId, Long userId);

    /**
     * 保存秒杀的订单，并返回orderId
     * @param info
     * @return
     */
    Long submitSeckillOrder(OrderInfo info);
}
