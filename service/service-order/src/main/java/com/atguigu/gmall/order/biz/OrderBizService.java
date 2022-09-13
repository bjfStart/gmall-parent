package com.atguigu.gmall.order.biz;

import com.atguigu.gmall.model.vo.order.OrderConfirmDataVo;
import com.atguigu.gmall.model.vo.order.OrderSubmitVo;

/**
 * @author feng
 * @create 2022-09-13 18:48
 */
public interface OrderBizService {
    /**
     * 获取确认订单页需要的数据
     * @return
     */
    OrderConfirmDataVo getConfirmData();

    /**
     * 生成交易流水号
     * 1.追踪订单
     * 2.防重令牌
     * @return
     */
    String generateTradeNo();

    /**
     * 校验令牌
     * @param tradeNo
     * @return
     */
    Boolean checkTradeNo(String tradeNo);

    /**
     * 提交订单
     * @param submitVo
     * @return
     */
    Long submitOrder(OrderSubmitVo submitVo,String tradeNo);
}
