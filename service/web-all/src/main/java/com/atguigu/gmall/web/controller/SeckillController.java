package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.feign.seckill.SeckillFeignClient;
import com.atguigu.gmall.model.activity.SeckillGoods;
import com.atguigu.gmall.model.vo.seckill.SeckillOrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 秒杀
 * @author feng
 * @create 2022-09-19 9:18
 */
@Controller
public class SeckillController {
    @Autowired
    SeckillFeignClient seckillFeignClient;
    /**
     * 来到秒杀列表页
     * @return
     */
    @GetMapping("/seckill.html")
    public String seckillPage(Model model){
        //TODO 查询秒杀数据
        //{skuId,skuDefaultImg,skuName,price,costPrice,num,stockCount}
        Result<List<SeckillGoods>> seckillGoodsList = seckillFeignClient.getCurrentDaySeckillGoodsList();
        model.addAttribute("list",seckillGoodsList.getData());
        return "seckill/index";
    }

    /**
     * 秒杀商品详情页
     * @param model
     * @param skuId
     * @return
     */
    @GetMapping("/seckill/{skuId}.html")
    public String seckillDetail(Model model, @PathVariable("skuId") Long skuId){
        Result<SeckillGoods> good = seckillFeignClient.getSeckillGood(skuId);
        model.addAttribute("item",good.getData());
        return "seckill/item";
    }

    /**
     * 秒杀排队页
     * @param skuId
     * @param skuIdStr
     * @return
     */
    @GetMapping("/seckill/queue.html")
    public String seckillQueue(@RequestParam("skuId") Long skuId,
                               @RequestParam("skuIdStr") String skuIdStr,
                               Model model){
        model.addAttribute("skuId",skuId);
        model.addAttribute("skuIdStr",skuIdStr);
        return "seckill/queue";
    }

    @GetMapping("/seckill/trade.html")
    public String trade(Model model,@RequestParam("skuId") Long skuId){
        Result<SeckillOrderConfirmVo> confirmVo = seckillFeignClient.getSeckillOrderConfirmVo(skuId);

        SeckillOrderConfirmVo voData = confirmVo.getData();

        //返回的是订单确认页的数据
        model.addAttribute("detailArrayList",voData.getTempOrder().getOrderDetailList());
        model.addAttribute("userAddressList",voData.getUserAddressList());
        model.addAttribute("totalName",voData.getTempOrder().getOrderDetailList().size());
        model.addAttribute("totalAmount",voData.getTempOrder().getTotalAmount());

        return "seckill/trade";
    }
}
