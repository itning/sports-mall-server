package com.sport.sportsmailserver.service;

import com.sport.sportsmailserver.entity.CommodityType;

import java.util.List;

/**
 * @author itning
 * @date 2020/2/12 12:27
 */
public interface CommodityTypeService {
    /**
     * 获取所有商品分类
     *
     * @return 分类集合
     */
    List<CommodityType> getAll();
}
