package com.atguigu.gmall.search.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.vo.search.SearchParamVo;
import com.atguigu.gmall.model.vo.search.SearchResponseVo;
import com.atguigu.gmall.search.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author feng
 * @create 2022-09-05 21:45
 */
@RestController
@RequestMapping("/api/inner/rpc/search")
public class SearchApiController {
    @Autowired
    GoodsService goodsService;
    @PostMapping("/goods")
    public Result saveGoods(@RequestBody Goods goods){
        goodsService.saveGoods(goods);
        return Result.ok();
    }

    @DeleteMapping("/goods/{skuId}")
    public Result deleteGoods(@PathVariable("skuId") Long skuId){
        goodsService.deleteGoods(skuId);
        return Result.ok();
    }

    /**
     * 尚品检索
     * @param paramVo
     * @return
     */
    @PostMapping("/goods/search")
    public Result<SearchResponseVo> search(@RequestBody SearchParamVo paramVo){
        SearchResponseVo searchResponseVo = goodsService.search(paramVo);
        //TODO 检索
        return Result.ok(searchResponseVo);
    }

    @GetMapping("/goods/hotscore/{skuId}")
    public Result updateHotScore(@PathVariable("skuId") Long skuId,
                                 @RequestParam("score") Long score,
                                 HttpServletResponse response){
        goodsService.updateHotScore(skuId,score);
        /**
         * 会话Cookie
         * 1）默认当亲啊绘画有效，只要浏览器关闭就销毁
         * 2）有个 Cookie都有自己的作用域范围
         */
        return Result.ok();

    }
}
