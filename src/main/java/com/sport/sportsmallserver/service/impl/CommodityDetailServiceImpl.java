package com.sport.sportsmallserver.service.impl;

import com.sport.sportsmallserver.entity.Commodity;
import com.sport.sportsmallserver.entity.CommodityDetail;
import com.sport.sportsmallserver.exception.IdNotFoundException;
import com.sport.sportsmallserver.exception.NullFiledException;
import com.sport.sportsmallserver.repository.CommodityDetailRepository;
import com.sport.sportsmallserver.service.CommodityDetailService;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public void modifyOrAdd(CommodityDetail commodityDetail) {
        if (StringUtils.isBlank(commodityDetail.getId())) {
            if (null == commodityDetail.getCommodity() || StringUtils.isBlank(commodityDetail.getCommodity().getId())) {
                throw new NullFiledException("商品ID为空");
            } else {
                Commodity commodity = new Commodity();
                commodity.setId(commodityDetail.getCommodity().getId());

                CommodityDetail newCommodityDetail = new CommodityDetail();
                newCommodityDetail.setCommodity(commodity);
                newCommodityDetail.setDetail(commodityDetail.getDetail());

                commodityDetailRepository.save(newCommodityDetail);
            }
        } else {
            CommodityDetail saved = commodityDetailRepository.findById(commodityDetail.getId()).orElseThrow(() -> new IdNotFoundException("详情ID不存在"));
            saved.setDetail(commodityDetail.getDetail());
            commodityDetailRepository.save(saved);
        }
    }
}
