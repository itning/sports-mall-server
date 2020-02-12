package com.sport.sportsmailserver.repository;

import com.sport.sportsmailserver.entity.Commodity;
import com.sport.sportsmailserver.entity.CommodityDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author itning
 * @date 2020/2/12 15:20
 */
public interface CommodityDetailRepository extends JpaRepository<CommodityDetail, String> {
    /**
     * 根据商品ID获取商品详情
     *
     * @param commodity 商品
     * @return 商品详情
     */
    Optional<CommodityDetail> findByCommodity(Commodity commodity);
}
