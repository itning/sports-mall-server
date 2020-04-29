package com.sport.sportsmallserver.controller;

import com.sport.sportsmallserver.dto.LoginUser;
import com.sport.sportsmallserver.dto.RestModel;
import com.sport.sportsmallserver.security.MustUserLogin;
import com.sport.sportsmallserver.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author itning
 * @date 2020/2/12 16:44
 */
@RestController
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * 添加到购物车
     *
     * @param loginUser   登录用户
     * @param commodityId 商品ID
     * @param num         数量
     * @param cumulative  累加
     * @return 添加的商品
     */
    @PostMapping("/cart")
    public ResponseEntity<?> addToCart(@MustUserLogin LoginUser loginUser,
                                       @RequestParam String commodityId,
                                       @RequestParam(defaultValue = "1") int num,
                                       @RequestParam(defaultValue = "true") boolean cumulative) {
        return RestModel.created(cartService.addToCart(loginUser, commodityId, num, cumulative));
    }

    /**
     * 获取某用户所有购物车
     *
     * @param loginUser 登录用户
     * @param pageable  分页
     * @return ResponseEntity
     */
    @GetMapping("/carts")
    public ResponseEntity<?> getCarts(@MustUserLogin LoginUser loginUser,
                                      @PageableDefault(
                                              size = 20, sort = {"gmtModified"},
                                              direction = Sort.Direction.DESC
                                      )
                                              Pageable pageable) {
        return RestModel.ok(cartService.getAll(loginUser, pageable));
    }

    /**
     * 删除购物车某个商品
     *
     * @param loginUser   登录用户
     * @param commodityId 商品ID
     * @return ResponseEntity
     */
    @DeleteMapping("/cart/{commodityId}")
    public ResponseEntity<?> delCart(@MustUserLogin LoginUser loginUser,
                                     @PathVariable String commodityId) {
        cartService.delCart(loginUser, commodityId);
        return RestModel.noContent();
    }
}
