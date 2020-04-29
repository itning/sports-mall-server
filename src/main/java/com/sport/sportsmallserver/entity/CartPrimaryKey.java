package com.sport.sportsmallserver.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>购物车复合主键
 * <p>所属实体{@link Cart}
 *
 * @author itning
 * @date 2020/2/11 20:41
 * @see Cart
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
