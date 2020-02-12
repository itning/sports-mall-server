package com.sport.sportsmailserver.service.impl;

import com.sport.sportsmailserver.entity.Commodity;
import com.sport.sportsmailserver.entity.CommodityType;
import com.sport.sportsmailserver.exception.IdNotFoundException;
import com.sport.sportsmailserver.exception.NullFiledException;
import com.sport.sportsmailserver.exception.SecurityServerException;
import com.sport.sportsmailserver.repository.CommodityRepository;
import com.sport.sportsmailserver.repository.CommodityTypeRepository;
import com.sport.sportsmailserver.service.CommodityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author itning
 * @date 2020/2/12 12:49
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommodityServiceImpl implements CommodityService {
    private final CommodityRepository commodityRepository;
    private final CommodityTypeRepository commodityTypeRepository;

    @Autowired
    public CommodityServiceImpl(CommodityRepository commodityRepository, CommodityTypeRepository commodityTypeRepository) {
        this.commodityRepository = commodityRepository;
        this.commodityTypeRepository = commodityTypeRepository;
    }

    @Override
    public Page<Commodity> findByType(String typeId, Pageable pageable) {
        if (StringUtils.isBlank(typeId)) {
            throw new NullFiledException("商品类别为空");
        }
        CommodityType commodityType = commodityTypeRepository.findById(typeId).orElseThrow(() -> new IdNotFoundException("商品类别没有找到"));
        return commodityRepository.findAllByCommodityTypeAndTakeOff(commodityType, false, pageable);
    }

    @Override
    public List<Commodity> findByRecommend() {
        return commodityRepository.findAllByRecommendedAndTakeOff(true, false);
    }

    @Override
    public Commodity findById(String id) {
        Commodity commodity = commodityRepository.findById(id).orElseThrow(() -> new IdNotFoundException("商品不存在"));
        if (commodity.isTakeOff()) {
            throw new SecurityServerException("商品已下架", HttpStatus.NOT_FOUND);
        }
        return commodity;
    }
}
