package com.sport.sportsmallserver.entity;

import com.sport.sportsmallserver.repository.CommodityRepository;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>商品（产品）
 * <p>已添加商品名索引
 *
 * @author itning
 * @date 2020/2/11 19:51
 * @see CommodityRepository
 * @see java.io.Serializable
 */
@Data
@Entity(name = "mail_commodity")
@Table(indexes = {@Index(name = "idx_name", columnList = "name")})
public class Commodity implements Serializable {
    /**
     * <p>商品ID 区分每个商品
     * <p>自动主键生成
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "char(36)")
    private String id;
    /**
     * 商品名
     */
    @Column(nullable = false)
    private String name;
    /**
     * 库存，默认为 1
     */
    @Column(nullable = false, columnDefinition = "int unsigned default 1")
    private int stock;
    /**
     * 销量，默认为 0
     */
    @Column(nullable = false, columnDefinition = "int unsigned default 0")
    private int sales;
    /**
     * <p>单价
     * <p>小数位数2位（角，分）
     */
    @Column(nullable = false, columnDefinition = "decimal(38,2)")
    private BigDecimal price;
    /**
     * 商品图片地址(主图片)
     */
    @Column(nullable = false, columnDefinition = "text")
    private String imgMain;
    /**
     * <p>其它图片地址(使用英文分号进行分隔)
     * <p>主要用于详情展示
     */
    @Column(nullable = false, columnDefinition = "text")
    private String imgSecond;
    /**
     * 推荐商品（会展示在首页）
     */
    @Column(nullable = false)
    private boolean recommended;
    /**
     * 商品已下架？
     */
    @Column(nullable = false, columnDefinition = "bit default false")
    private boolean takeOff;
    /**
     * 商品分类
     */
    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne(optional = false)
    @JoinColumn(name = "commodityTypeId")
    private CommodityType commodityType;
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
