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

    /**
     * 修改分类信息
     *
     * @param commodityType 分类
     */
    void modifyCommodityType(CommodityType commodityType);

    /**
     * 删除分类
     *
     * @param id 编号
     */
    void delCommodityType(String id);

    /**
     * 添加分类
     *
     * @param name 分类名
     * @return 新增的分类
     */
    CommodityType add(String name);
}
