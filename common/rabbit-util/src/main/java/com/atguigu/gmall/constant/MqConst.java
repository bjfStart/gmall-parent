package com.atguigu.gmall.constant;

/**
 * @author feng
 * @create 2022-09-15 11:28
 */
public class MqConst {
    //订单交换机
    public static final String ORDER_EVENT_EXCHANGE = "order_event_exchange";
    //订单延迟队列
    public static final String ORDER_DELAY_QUEUE = "order_delay_queue";
    //死信路由
    public static final String RK_ORDER_DEAD = "order.dead" ;
    //订单的绑定的路由
    public static final String RK_ORDER_CREATED = "order.created";
    //死信队列
    public static final String ORDER_DEAD_QUEUE = "order_dead_queue";
    //订单支付成功的绑定路由
    public static final String RK_ORDER_PAYED = "order.payed";
    public static final String ORDER_PAYED_QUEUE = "order_payed_queue";

    //库存交换机
    public static final String EXCHANGE_WARE_EVENT = "exchange.direct.ware.stock";
    //减库存路由键
    public static final String RK_WARE_DEDUCE = "ware.stock";
    //库存扣减结果队列
    public static final String ORDER_WARE_QUEUE = "queue.ware.order";
    //库存扣减交换机
    public static final String ORDER_WARE_EXCHANGE = "exchange.direct.ware.order";
    //库存扣减路由键
    public static final String RK_WARE_ORDER = "ware.order";
}
