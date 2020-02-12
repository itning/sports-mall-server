package com.sport.sportsmailserver.service.impl;

import com.sport.sportsmailserver.entity.Commodity;
import com.sport.sportsmailserver.entity.CommodityDetail;
import com.sport.sportsmailserver.repository.CommodityDetailRepository;
import com.sport.sportsmailserver.service.CommodityDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author itning
 * @date 2020/2/12 15:33
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommodityDetailServiceImpl implements CommodityDetailService {
    private final CommodityDetailRepository commodityDetailRepository;

    @Autowired
    public CommodityDetailServiceImpl(CommodityDetailRepository commodityDetailRepository) {
        this.commodityDetailRepository = commodityDetailRepository;
    }

    @Override
    public CommodityDetail findByCommodityId(String commodityId) {
        Commodity commodity = new Commodity();
        commodity.setId(commodityId);
        return commodityDetailRepository.findByCommodity(commodity).orElse(new CommodityDetail());
    }
}
