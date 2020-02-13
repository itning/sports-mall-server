package com.sport.sportsmailserver.dto;

import com.sport.sportsmailserver.entity.CommodityType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author itning
 * @date 2020/2/13 22:08
 */
@Data
public class CommodityDTO implements Serializable {
    /**
     * <p>商品ID 区分每个商品
     * <p>自动主键生成
     */
    private String id;
    /**
     * 商品名
     */
    private String name;
    /**
     * 库存，默认为 1
     */
    private Integer stock;
    /**
     * <p>单价
     * <p>小数位数2位（角，分）
     */
    private BigDecimal price;
    /**
     * 商品图片地址(主图片)
     */
    private String imgMain;
    /**
     * <p>其它图片地址(使用英文分号进行分隔)
     * <p>主要用于详情展示
     */
    private String imgSecond;
    /**
     * 推荐商品（会展示在首页）
     */
    private Boolean recommended;
    /**
     * 商品已下架？
     */
    private Boolean takeOff;
    /**
     * 商品分类
     */
    private CommodityType commodityType;
}
