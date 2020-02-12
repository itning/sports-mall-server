package com.sport.sportsmailserver.service.impl;

import com.sport.sportsmailserver.dto.LoginUser;
import com.sport.sportsmailserver.entity.Cart;
import com.sport.sportsmailserver.entity.CartPrimaryKey;
import com.sport.sportsmailserver.entity.Commodity;
import com.sport.sportsmailserver.entity.User;
import com.sport.sportsmailserver.exception.IdNotFoundException;
import com.sport.sportsmailserver.exception.SecurityServerException;
import com.sport.sportsmailserver.repository.CartRepository;
import com.sport.sportsmailserver.repository.CommodityRepository;
import com.sport.sportsmailserver.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author itning
 * @date 2020/2/12 16:46
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CommodityRepository commodityRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CommodityRepository commodityRepository) {
        this.cartRepository = cartRepository;
        this.commodityRepository = commodityRepository;
    }

    @Override
    public Cart addToCart(LoginUser loginUser, String commodityId, int num, boolean cumulative) {
        Commodity cc = commodityRepository.findById(commodityId).orElseThrow(() -> new IdNotFoundException("商品不存在"));
        if (cc.isTakeOff()) {
            throw new SecurityServerException("商品已下架", HttpStatus.NOT_FOUND);
        }
        CartPrimaryKey cartPrimaryKey = new CartPrimaryKey();
        cartPrimaryKey.setUser(loginUser.getUsername());
        cartPrimaryKey.setCommodity(commodityId);
        Optional<Cart> cartOptional = cartRepository.findById(cartPrimaryKey);
        Cart cart;
        if (cartOptional.isPresent()) {
            // 商品已经在购物车里了
            cart = cartOptional.get();
            if (cumulative) {
                cart.setCountNum(cart.getCountNum() + num);
            } else {
                cart.setCountNum(num);
            }
        } else {
            User user = new User();
            user.setUsername(loginUser.getUsername());

            cart = new Cart();
            cart.setCountNum(num);
            cart.setUser(user);
            cart.setCommodity(cc);
        }
        return cartRepository.save(cart);
    }

    @Override
    public Page<Cart> getAll(LoginUser loginUser, Pageable pageable) {
        User user = new User();
        user.setUsername(loginUser.getUsername());
        return cartRepository.findAllByUser(user, pageable);
    }

    @Override
    public void delCart(LoginUser loginUser, String commodityId) {
        Commodity cc = commodityRepository.findById(commodityId).orElseThrow(() -> new IdNotFoundException("商品不存在"));

        User user = new User();
        user.setUsername(loginUser.getUsername());

        cartRepository.deleteByCommodityAndUser(cc, user);
    }
}
