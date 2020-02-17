package com.sport.sportsmailserver.controller;

import com.sport.sportsmailserver.dto.LoginUser;
import com.sport.sportsmailserver.dto.RestModel;
import com.sport.sportsmailserver.security.MustAdminLogin;
import com.sport.sportsmailserver.security.MustLogin;
import com.sport.sportsmailserver.security.MustUserLogin;
import com.sport.sportsmailserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 订单
 *
 * @author itning
 * @date 2020/2/12 19:02
 */
@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 下订单
     *
     * @param loginUser   登录用户
     * @param commodityId 商品ID
     * @param count       数量
     * @param address     收货地址
     * @return ResponseEntity
     */
    @PostMapping("/order")
    public ResponseEntity<?> newOrder(@MustUserLogin LoginUser loginUser,
                                      @RequestParam String commodityId,
                                      @RequestParam int count,
                                      @RequestParam String address) {
        return RestModel.created(orderService.newOrder(loginUser, commodityId, count, address));
    }

    /**
     * 获取所有订单
     *
     * @param loginUser 登录用户
     * @param pageable  分页
     * @return ResponseEntity
     */
    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders(@MustUserLogin LoginUser loginUser,
                                          @RequestParam(required = false) int[] status,
                                          @PageableDefault(
                                                  size = 20, sort = {"gmtModified"},
                                                  direction = Sort.Direction.DESC
                                          )
                                                  Pageable pageable) {
        return RestModel.ok(orderService.getAll(loginUser, status, pageable));
    }

    /**
     * 删除订单
     *
     * @param loginUser 登录用户
     * @param id        订单ID
     * @return ResponseEntity
     */
    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> delOrder(@MustLogin(role = {MustLogin.ROLE.ADMIN, MustLogin.ROLE.USER}) LoginUser loginUser,
                                      @PathVariable String id) {
        orderService.delOrder(loginUser, id);
        return RestModel.noContent();
    }

    /**
     * 订单付款
     *
     * @param loginUser 登录用户
     * @param orderId   订单ID
     * @return ResponseEntity
     */
    @PostMapping("/order/pay")
    public ResponseEntity<?> payOrder(@MustUserLogin LoginUser loginUser,
                                      @RequestParam String orderId) {
        return RestModel.created(orderService.pay(loginUser, orderId));
    }

    /**
     * 订单发货
     *
     * @param loginUser          登录用户
     * @param orderId            订单ID
     * @param expressInformation 快递信息
     * @return ResponseEntity
     */
    @PostMapping("/order/hip")
    public ResponseEntity<?> hipOrder(@MustAdminLogin LoginUser loginUser,
                                      @RequestParam String orderId,
                                      @RequestParam String expressInformation) {
        return RestModel.created(orderService.ship(orderId, expressInformation));
    }

    /**
     * 订单确认收货
     *
     * @param loginUser 登录用户
     * @param orderId   订单ID
     * @return ResponseEntity
     */
    @PostMapping("/order/receipt")
    public ResponseEntity<?> receiptOrder(@MustUserLogin LoginUser loginUser,
                                          @RequestParam String orderId) {
        return RestModel.created(orderService.receipt(loginUser, orderId));
    }

    /**
     * 管理员获取订单信息
     *
     * @param loginUser 登录用户
     * @param status    要获取的订单状态
     * @param pageable  分页
     * @return ResponseEntity
     */
    @GetMapping("/orders/admin")
    public ResponseEntity<?> getAllOrderForAdmin(@MustAdminLogin LoginUser loginUser,
                                                 @RequestParam(required = false) int[] status,
                                                 @PageableDefault(
                                                         size = 20, sort = {"gmtModified"},
                                                         direction = Sort.Direction.DESC
                                                 )
                                                         Pageable pageable) {
        return RestModel.ok(orderService.getAll(status, pageable));
    }

    /**
     * 修改订单
     *
     * @param loginUser 登录用户
     * @param id        订单ID
     * @param newPrice  新价格
     * @return ResponseEntity
     */
    @PostMapping("/order/price")
    public ResponseEntity<?> changeOrderPrice(@MustAdminLogin LoginUser loginUser,
                                              @RequestParam String id,
                                              @RequestParam BigDecimal newPrice) {
        return RestModel.created(orderService.changePrice(id, newPrice));
    }
}
