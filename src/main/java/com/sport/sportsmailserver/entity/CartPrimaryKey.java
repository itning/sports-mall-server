package com.sport.sportsmailserver.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>购物车复合主键
 * <p>所属实体{@link com.sport.sportsmailserver.entity.Cart}
 *
 * @author itning
 * @date 2020/2/11 20:41
 * @see com.sport.sportsmailserver.entity.Cart
 * @see java.io.Serializable
 */
@Data
public class CartPrimaryKey implements Serializable {
    /**
     * 用户
     */
    private String user;
    /**
     * 商品
     */
    private String commodity;
}
