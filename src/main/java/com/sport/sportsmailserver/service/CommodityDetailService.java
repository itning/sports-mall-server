package com.sport.sportsmailserver.service;

import com.sport.sportsmailserver.entity.CommodityDetail;

/**
 * 商品详情服务
 *
 * @author itning
 * @date 2020/2/12 15:32
 */
public interface CommodityDetailService {
    /**
     * 根据商品ID获取商品详情
     *
     * @param commodityId 商品ID
     * @return 商品详情
     */
    CommodityDetail findByCommodityId(String commodityId);
}
