package com.sport.sportsmallserver.entity;

import com.sport.sportsmallserver.repository.CommodityTypeRepository;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品分类
 *
 * @author itning
 * @date 2020/2/11 21:54
 * @see CommodityTypeRepository
 * @see java.io.Serializable
 */
@Data
@Entity(name = "mail_commodity_type")
public class CommodityType implements Serializable {
    /**
     * 分类ID
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "char(36)")
    private String id;
    /**
     * 商品分类名
     */
    @Column(nullable = false)
    private String name;
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
