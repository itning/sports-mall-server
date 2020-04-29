package com.sport.sportsmallserver.service;

import com.sport.sportsmallserver.dto.LoginUser;
import com.sport.sportsmallserver.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * @author itning
 * @date 2020/2/12 19:12
 */
public interface OrderService {
    /**
     * 下订单
     *
     * @param loginUser   登录用户
     * @param commodityId 商品ID
     * @param count       数量
     * @param address     收货地址
     * @return 新订单
     */
    Order newOrder(LoginUser loginUser, String commodityId, int count, String address);

    /**
     * 获取所有订单
     *
     * @param loginUser 登录用户
     * @param status    要的订单状态
     * @param pageable  分页
     * @return 所有订单
     */
    Page<Order> getAll(LoginUser loginUser, int[] status, Pageable pageable);

    /**
     * 删除订单
     *
     * @param loginUser 登录用户
     * @param orderId   订单ID
     */
    void delOrder(LoginUser loginUser, String orderId);

    /**
     * 订单付款
     *
     * @param loginUser 登录用户
     * @param orderId   订单ID
     * @return 订单
     */
    Order pay(LoginUser loginUser, String orderId);

    /**
     * 订单发货
     *
     * @param orderId            订单ID
     * @param expressInformation 快递信息
     * @return 订单
     */
    Order ship(String orderId, String expressInformation);

    /**
     * 订单确认收获
     *
     * @param loginUser 登录用户
     * @param orderId   订单ID
     * @return 订单
     */
    Order receipt(LoginUser loginUser, String orderId);

    /**
     * 获取所有订单
     *
     * @param status   订单状态
     * @param pageable 分页
     * @return 订单集合
     */
    Page<Order> getAll(int[] status, Pageable pageable);

    /**
     * 修改订单总价格
     *
     * @param id       订单ID
     * @param newPrice 新价格
     * @return 新订单
     */
    Order changePrice(String id, BigDecimal newPrice);
}
