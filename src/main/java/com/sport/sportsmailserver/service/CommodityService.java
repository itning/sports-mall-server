package com.sport.sportsmailserver.service;

import com.sport.sportsmailserver.dto.CommodityDTO;
import com.sport.sportsmailserver.entity.Commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author itning
 * @date 2020/2/12 12:48
 */
public interface CommodityService {
    /**
     * 根据商品类别查找商品
     *
     * @param typeId   类别ID
     * @param pageable 分页
     * @return 该类别下所有商品
     */
    Page<Commodity> findByType(String typeId, Pageable pageable);

    /**
     * 查找所有推荐商品
     *
     * @return 推荐商品集合
     */
    List<Commodity> findByRecommend();

    /**
     * 根据商品ID查询商品
     *
     * @param id 商品ID
     * @return 商品
     */
    Commodity findById(String id);

    /**
     * 搜索商品
     *
     * @param keyword  关键字
     * @param pageable 分页
     * @return 商品集合
     */
    Page<Commodity> search(String keyword, Pageable pageable);

    /**
     * 获取所有商品
     *
     * @param pageable 分页
     * @return 商品集合
     */
    Page<Commodity> findAll(Pageable pageable);

    /**
     * 修改商品
     *
     * @param commodity 商品
     */
    void modify(CommodityDTO commodity);
}
