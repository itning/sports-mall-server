package com.sport.sportsmailserver.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 购物车
 *
 * @author itning
 * @date 2020/2/11 20:38
 * @see com.sport.sportsmailserver.repository.CartRepository
 * @see java.io.Serializable
 */
@Data
@Entity(name = "mail_cart")
@IdClass(CartPrimaryKey.class)
public class Cart implements Serializable {
    /**
     * 用户
     */
    @SuppressWarnings("JpaDataSourceORMInspection")
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", columnDefinition = "varchar(255)")
    private User user;
    /**
     * 商品
     */
    @SuppressWarnings("JpaDataSourceORMInspection")
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "commodityId", columnDefinition = "char(36)")
    private Commodity commodity;
    /**
     * 数量
     */
    @Column(nullable = false, columnDefinition = "int unsigned default 1")
    private int countNum;
    /**
     * 创建时间
     */
    @Column(nullable = false)
    @CreationTimestamp
    private Date gmtCreate;
    /**
     * 更新时间
     */
    @Column(nullable = false)
    @UpdateTimestamp
    private Date gmtModified;
}
