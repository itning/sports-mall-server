package com.sport.sportsmailserver.repository;

import com.sport.sportsmailserver.entity.Commodity;
import com.sport.sportsmailserver.entity.CommodityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author itning
 * @date 2020/2/11 20:17
 */
public interface CommodityRepository extends JpaRepository<Commodity, String> {
    /**
     * 根据商品分类获取商品
     *
     * @param commodityType 商品类别
     * @param takeOff       下架的商品？
     * @param pageable      分页
     * @return 商品集合
     */
    Page<Commodity> findAllByCommodityTypeAndTakeOff(CommodityType commodityType, boolean takeOff, Pageable pageable);

    /**
     * 更具是否推荐和是否下架获取商品
     *
     * @param recommended 推荐商品
     * @param takeOff     下架的商品？
     * @return 商品集合
     */
    List<Commodity> findAllByRecommendedAndTakeOff(boolean recommended, boolean takeOff);
}
