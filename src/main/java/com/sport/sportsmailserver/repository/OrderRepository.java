package com.sport.sportsmailserver.repository;

import com.sport.sportsmailserver.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author itning
 * @date 2020/2/11 21:19
 */
public interface OrderRepository extends JpaRepository<Order, String> {
}
