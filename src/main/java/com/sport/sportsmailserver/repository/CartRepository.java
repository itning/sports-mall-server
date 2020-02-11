package com.sport.sportsmailserver.repository;

import com.sport.sportsmailserver.entity.Cart;
import com.sport.sportsmailserver.entity.CartPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author itning
 * @date 2020/2/11 21:12
 */
public interface CartRepository extends JpaRepository<Cart, CartPrimaryKey> {
}
