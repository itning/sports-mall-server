package com.sport.sportsmallserver.service;

import com.sport.sportsmallserver.dto.LoginUser;
import com.sport.sportsmallserver.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 购物车服务
 *
 * @author itning
 * @date 2020/2/12 16:45
 */
public interface CartService {
    /**
     * <p>商品加入购物车
     * <p>每调用一次商品数量变为num
     *
     * @param loginUser   登录用户
     * @param commodityId 商品ID
     * @param num         数量
     * @param cumulative  累加
     * @return 加入的购物车
     */
    Cart addToCart(LoginUser loginUser, String commodityId, int num, boolean cumulative);

    /**
     * 获取所有购物车
     *
     * @param loginUser 登录用户
     * @param pageable  分页
     * @return 购物车集合
     */
    Page<Cart> getAll(LoginUser loginUser, Pageable pageable);

    /**
     * 删除购物车某样商品
     *
     * @param loginUser   登录用户
     * @param commodityId 商品ID
     */
    void delCart(LoginUser loginUser, String commodityId);
}
