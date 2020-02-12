package com.sport.sportsmailserver.service;

import com.sport.sportsmailserver.dto.LoginUser;
import com.sport.sportsmailserver.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param loginUser          登录用户
     * @param orderId            订单ID
     * @param expressInformation 快递信息
     * @return 订单
     */
    Order ship(LoginUser loginUser, String orderId, String expressInformation);

    /**
     * 订单确认收获
     *
     * @param loginUser 登录用户
     * @param orderId   订单ID
     * @return 订单
     */
    Order receipt(LoginUser loginUser, String orderId);
}
