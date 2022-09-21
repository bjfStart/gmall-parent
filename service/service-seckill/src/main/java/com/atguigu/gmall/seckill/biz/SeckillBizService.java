package com.atguigu.gmall.seckill.biz;

import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.model.vo.seckill.SeckillOrderConfirmVo;

/**
 * @author feng
 * @create 2022-09-19 14:03
 */
public interface SeckillBizService {


    /**
     * 校验生成的秒杀码
     * @param skuId
     * @param code
     * @return
     */
    boolean checkSeckillCode(Long skuId,String code);

    /**
     * 生成秒杀码
     * @param skuId
     * @return
     */
    String generateSeckillCode(Long skuId);

    /**
     * 秒杀下单
     * @param skuId
     * @param skuIdStr
     * @return
     */
    ResultCodeEnum seckillOrder(Long skuId, String skuIdStr);

    /**
     * 检查秒杀订单的状态
     * @param skuId
     * @return
     */
    ResultCodeEnum checkSeckillOrderStatus(Long skuId);

    /**
     * 获取秒杀确认页数据
     * @param skuId
     * @return
     */
    SeckillOrderConfirmVo getSeckillOrderConfirmVo(Long skuId);

    /**
     * 保存秒杀单信息
     * @param orderInfo
     * @return
     */
    Long submitSeckillOrder(OrderInfo orderInfo);
}
