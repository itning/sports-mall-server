package com.sport.sportsmailserver.service.impl;

import com.sport.sportsmailserver.dto.CommodityDTO;
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

import java.util.Arrays;
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

    @Override
    public Page<Commodity> search(String keyword, Pageable pageable) {
        if (StringUtils.isBlank(keyword)) {
            throw new NullFiledException("关键字不能为空");
        }
        return commodityRepository.findAllByTakeOffAndNameLike(false, "%" + keyword + "%", pageable);
    }

    @Override
    public Page<Commodity> findAll(Pageable pageable) {
        return commodityRepository.findAllByTakeOff(false, pageable);
    }

    @Override
    public void modify(CommodityDTO commodity) {
        if (StringUtils.isBlank(commodity.getId())) {
            throw new NullFiledException("商品ID为空");
        }
        Commodity saved = commodityRepository.findById(commodity.getId()).orElseThrow(() -> new IdNotFoundException("商品不存在"));
        // 1.库存变
        if (commodity.getStock() != null) {
            saved.setStock(commodity.getStock());
        }
        // 2.价格变
        if (commodity.getPrice() != null) {
            saved.setPrice(commodity.getPrice());
        }
        // 3.标题变
        if (StringUtils.isNotBlank(commodity.getName())) {
            saved.setName(commodity.getName());
        }
        // 4.主图片变
        if (StringUtils.isNotBlank(commodity.getImgMain())) {
            saved.setImgMain(commodity.getImgMain());
        }
        // 5.推荐变
        if (commodity.getRecommended() != null) {
            saved.setRecommended(commodity.getRecommended());
        }
        // 6.商品下架？
        if (commodity.getTakeOff() != null) {
            saved.setTakeOff(commodity.getTakeOff());
        }
        // 7.副图片变
        if (commodity.getImgSecond() != null) {
            try {
                String[] split = commodity.getImgSecond().split(";");
                System.out.println(Arrays.toString(split));
            } catch (Exception e) {
                throw new SecurityServerException("副图片格式错误", HttpStatus.BAD_REQUEST);
            }
            saved.setImgSecond(commodity.getImgSecond());
        }
        // 8.商品分类变
        if (commodity.getCommodityType() != null && commodity.getCommodityType().getId() != null) {
            CommodityType commodityType = commodityTypeRepository.findById(commodity.getCommodityType().getId()).orElseThrow(() -> new IdNotFoundException("商品分类没找到"));
            saved.setCommodityType(commodityType);
        }
        commodityRepository.save(saved);
    }
}
