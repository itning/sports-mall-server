package com.sport.sportsmailserver.service.impl;

import com.sport.sportsmailserver.entity.CommodityType;
import com.sport.sportsmailserver.repository.CommodityTypeRepository;
import com.sport.sportsmailserver.service.CommodityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author itning
 * @date 2020/2/12 12:28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommodityTypeServiceImpl implements CommodityTypeService {
    private final CommodityTypeRepository commodityTypeRepository;

    @Autowired
    public CommodityTypeServiceImpl(CommodityTypeRepository commodityTypeRepository) {
        this.commodityTypeRepository = commodityTypeRepository;
    }

    @Override
    public List<CommodityType> getAll() {
        return commodityTypeRepository.findAll();
    }
}
