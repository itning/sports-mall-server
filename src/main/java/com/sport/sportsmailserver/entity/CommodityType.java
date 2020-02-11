package com.sport.sportsmailserver.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品分类
 *
 * @author itning
 * @date 2020/2/11 21:54
 * @see com.sport.sportsmailserver.repository.CommodityTypeRepository
 * @see java.io.Serializable
 */
@Data
@Entity(name = "mail_commodity_type")
public class CommodityType implements Serializable {
    /**
     * 商品分类名
     */
    @Id
    private String name;
    /**
     * 该分类下所有商品
     */
    @SuppressWarnings("JpaDataSourceORMInspection")
    @OneToMany
    @JoinColumn(name = "commodityTypeId")
    private List<Commodity> commodities;
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
