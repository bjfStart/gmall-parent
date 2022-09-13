package com.atguigu.gmall.cart.service;

import com.atguigu.gmall.model.cart.CartInfo;
import com.atguigu.gmall.model.product.SkuInfo;

import java.util.List;

/**
 * @author feng
 * @create 2022-09-08 19:43
 */
public interface CartService {
    SkuInfo addToCart(Long skuId, Integer num);

    /**
     * 根据用户登录信息决定用哪个购物车键
     * @return
     */
    String determinCartKey();

    /**
     * 将商品添加到指定购物车
     * @param skuId
     * @param num
     * @param cartKey
     * @return
     */
    SkuInfo addItemToCart(Long skuId, Integer num, String cartKey);

    /**
     * 获取购物车中的商品信息
     * @param cartKey
     * @param skuId
     * @return
     */
    CartInfo getItemFromCart(String cartKey, Long skuId);

    /**
     * 获取指定购物车中的所有商品，排好序（按照createTime顺序）
     * @param cartKey
     * @return
     */
    List<CartInfo> getCartList(String cartKey);

    /**
     * 修改购物车某个商品的数量
     * @param skuId
     * @param num
     * @param cartKey
     */
    void updateItemNum(Long skuId, Integer num, String cartKey);

    /**
     * 修改购物车某个商品是否被选中
     * @param skuId
     * @param status
     * @param cartKey
     */
    void updateChecked(Long skuId, Integer status, String cartKey);

    /**
     * 删除购车中的商品
      * @param skuId
     * @param cartKey
     */
    void deleteCartItem(Long skuId, String cartKey);

    /**
     * 删除购物车中选中的商品
     * @param cartKey
     */
    void deleteChecked(String cartKey);

    /**
     * 获取购物车中被选中的id列表
     * @param cartKey
     * @return
     */
    List<CartInfo> getCheckedItems(String cartKey);

    void mergeUserAndTempCart();

    /**
     * 查询购物车所有商品的实时价格
     * @param cartKey
     */
    void updateCartAllItemsPrice(String cartKey);
}
