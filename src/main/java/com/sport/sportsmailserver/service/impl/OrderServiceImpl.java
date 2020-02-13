package com.sport.sportsmailserver.service.impl;

import com.sport.sportsmailserver.dto.LoginUser;
import com.sport.sportsmailserver.entity.Commodity;
import com.sport.sportsmailserver.entity.Order;
import com.sport.sportsmailserver.entity.Role;
import com.sport.sportsmailserver.entity.User;
import com.sport.sportsmailserver.exception.IdNotFoundException;
import com.sport.sportsmailserver.exception.SecurityServerException;
import com.sport.sportsmailserver.repository.CommodityRepository;
import com.sport.sportsmailserver.repository.OrderRepository;
import com.sport.sportsmailserver.repository.UserRepository;
import com.sport.sportsmailserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author itning
 * @date 2020/2/12 19:13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CommodityRepository commodityRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CommodityRepository commodityRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.commodityRepository = commodityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order newOrder(LoginUser loginUser, String commodityId, int count, String address) {
        Commodity commodity = commodityRepository.findById(commodityId).orElseThrow(() -> new IdNotFoundException("商品不存在"));
        if (commodity.isTakeOff()) {
            throw new SecurityServerException("商品已下架", HttpStatus.NOT_FOUND);
        }
        if (commodity.getStock() < count) {
            throw new SecurityServerException("库存不足", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(loginUser.getUsername());
        // 减库存
        commodity.setStock(commodity.getStock() - count);
        // 加销量
        commodity.setSales(commodity.getSales() + count);

        Order order = new Order();
        order.setUser(user);
        order.setCommodity(commodity);
        order.setCountNum(count);
        order.setSumPrice(getTotalPrice(commodity.getPrice(), count));
        order.setStatus(Order.STATUS.ORDERED.getStatus());

        commodityRepository.save(commodity);
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> getAll(LoginUser loginUser, int[] status, Pageable pageable) {
        final int[] ss = getStatus(status);
        return orderRepository.findAll((Specification<Order>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            Join<Order, User> orderUserJoin = root.join("user", JoinType.INNER);
            list.add(cb.equal(orderUserJoin.get("username"), loginUser.getUsername()));

            List<Predicate> or = new ArrayList<>(ss.length);
            for (int s : ss) {
                or.add(cb.equal(root.get("status"), s));
            }
            Predicate[] oo = new Predicate[or.size()];
            list.add(cb.or(or.toArray(oo)));

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }, pageable);
    }

    @Override
    public void delOrder(LoginUser loginUser, String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IdNotFoundException("订单不存在"));
        User user = userRepository.findById(loginUser.getUsername()).orElseThrow(() -> new IdNotFoundException("用户不存在"));
        if (user.getRole().getId().equals(Role.ROLE_ADMIN_ID)) {
            if (order.getStatus() == Order.STATUS.DEL_BY_USER.getStatus()) {
                order.setStatus(Order.STATUS.DEL_ALL.getStatus());
            } else {
                order.setStatus(Order.STATUS.DEL_BY_ADMIN.getStatus());
            }
        } else {
            if (!order.getUser().getUsername().equals(loginUser.getUsername())) {
                throw new SecurityServerException("操作失败", HttpStatus.FORBIDDEN);
            }
            if (order.getStatus() == Order.STATUS.DEL_BY_ADMIN.getStatus()) {
                order.setStatus(Order.STATUS.DEL_ALL.getStatus());
            } else {
                order.setStatus(Order.STATUS.DEL_BY_USER.getStatus());
            }
        }
        orderRepository.save(order);
    }

    @Override
    public Order pay(LoginUser loginUser, String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IdNotFoundException("订单不存在"));
        if (!order.getUser().getUsername().equals(loginUser.getUsername())) {
            throw new SecurityServerException("操作失败", HttpStatus.FORBIDDEN);
        }
        if (order.getStatus() != Order.STATUS.ORDERED.getStatus()) {
            throw new SecurityServerException("订单已经付款或被删除", HttpStatus.BAD_REQUEST);
        }
        order.setStatus(Order.STATUS.BUY.getStatus());
        return orderRepository.save(order);
    }

    @Override
    public Order ship(String orderId, String expressInformation) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IdNotFoundException("订单不存在"));
        if (order.getStatus() != Order.STATUS.BUY.getStatus()) {
            throw new SecurityServerException("订单未付款或被删除", HttpStatus.BAD_REQUEST);
        }
        order.setStatus(Order.STATUS.SHIP.getStatus());
        return orderRepository.save(order);
    }

    @Override
    public Order receipt(LoginUser loginUser, String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IdNotFoundException("订单不存在"));
        if (!order.getUser().getUsername().equals(loginUser.getUsername())) {
            throw new SecurityServerException("操作失败", HttpStatus.FORBIDDEN);
        }
        if (order.getStatus() != Order.STATUS.SHIP.getStatus()) {
            throw new SecurityServerException("订单已收货或被删除", HttpStatus.BAD_REQUEST);
        }
        order.setStatus(Order.STATUS.RECEIPT.getStatus());
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> getAll(int[] status, Pageable pageable) {
        final int[] ss = getStatus(status);
        return orderRepository.findAll((Specification<Order>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            for (int s : ss) {
                list.add(cb.equal(root.get("status"), s));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.or(list.toArray(p));
        }, pageable);
    }

    private BigDecimal getTotalPrice(BigDecimal price, int count) {
        return price.multiply(BigDecimal.valueOf(count));
    }

    private int[] getStatus(int[] status) {
        if (null == status || status.length == 0) {
            status = new int[]{
                    Order.STATUS.ORDERED.getStatus(),
                    Order.STATUS.BUY.getStatus(),
                    Order.STATUS.SHIP.getStatus(),
                    Order.STATUS.RECEIPT.getStatus(),
                    Order.STATUS.EVALUATION.getStatus(),
            };
        }
        return Arrays.stream(status)
                .filter(i -> i != Order.STATUS.DEL_ALL.getStatus())
                .filter(i -> i != Order.STATUS.DEL_BY_USER.getStatus())
                .filter(i -> i != Order.STATUS.DEL_BY_ADMIN.getStatus())
                .toArray();
    }
}
