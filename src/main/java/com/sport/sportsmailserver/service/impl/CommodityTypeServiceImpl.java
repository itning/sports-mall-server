package com.sport.sportsmailserver.service.impl;

import com.sport.sportsmailserver.entity.CommodityType;
import com.sport.sportsmailserver.exception.IdNotFoundException;
import com.sport.sportsmailserver.exception.NullFiledException;
import com.sport.sportsmailserver.exception.SecurityServerException;
import com.sport.sportsmailserver.repository.CommodityRepository;
import com.sport.sportsmailserver.repository.CommodityTypeRepository;
import com.sport.sportsmailserver.service.CommodityTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final CommodityRepository commodityRepository;

    @Autowired
    public CommodityTypeServiceImpl(CommodityTypeRepository commodityTypeRepository, CommodityRepository commodityRepository) {
        this.commodityTypeRepository = commodityTypeRepository;
        this.commodityRepository = commodityRepository;
    }

    @Override
    public List<CommodityType> getAll() {
        return commodityTypeRepository.findAll();
    }

    @Override
    public void modifyCommodityType(CommodityType commodityType) {
        if (StringUtils.isAnyBlank(commodityType.getName(), commodityType.getId())) {
            throw new NullFiledException("分类名或ID为空");
        }
        CommodityType ct = commodityTypeRepository.findById(commodityType.getId()).orElseThrow(() -> new IdNotFoundException("分类没有找到"));
        if (ct.getName().equals(commodityType.getName())) {
            throw new SecurityServerException("原名称和新名称相同", HttpStatus.BAD_REQUEST);
        }
        ct.setName(commodityType.getName());
        commodityTypeRepository.save(ct);
    }

    @Override
    public void delCommodityType(String id) {
        CommodityType commodityType = commodityTypeRepository.findById(id).orElseThrow(() -> new IdNotFoundException("分类没有找到"));
        if (commodityRepository.existsByCommodityType(commodityType)) {
            throw new SecurityServerException("该分类下还有商品", HttpStatus.BAD_REQUEST);
        }
        commodityTypeRepository.delete(commodityType);
    }

    @Override
    public CommodityType add(String name) {
        if (StringUtils.isBlank(name)) {
            throw new NullFiledException("名称不能为空");
        }
        CommodityType commodityType = new CommodityType();
        commodityType.setName(name);
        return commodityTypeRepository.save(commodityType);
    }
}
