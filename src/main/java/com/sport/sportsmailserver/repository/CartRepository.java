package com.sport.sportsmailserver.repository;

import com.sport.sportsmailserver.entity.Cart;
import com.sport.sportsmailserver.entity.CartPrimaryKey;
import com.sport.sportsmailserver.entity.Commodity;
import com.sport.sportsmailserver.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author itning
 * @date 2020/2/11 21:12
 */
public interface CartRepository extends JpaRepository<Cart, CartPrimaryKey> {
    /**
     * 查找某个用户的购物车信息
     *
     * @param user     用户
     * @param pageable 分页
     * @return 购物车集合
     */
    Page<Cart> findAllByUser(User user, Pageable pageable);

    /**
     * 删除购物车某样商品
     *
     * @param commodity 商品
     * @param user      用户
     */
    void deleteByCommodityAndUser(Commodity commodity, User user);
}
