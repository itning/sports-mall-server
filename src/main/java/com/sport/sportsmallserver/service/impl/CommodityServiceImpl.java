package com.sport.sportsmallserver.service.impl;

import com.sport.sportsmallserver.dto.CommodityDTO;
import com.sport.sportsmallserver.entity.Commodity;
import com.sport.sportsmallserver.entity.CommodityDetail;
import com.sport.sportsmallserver.entity.CommodityType;
import com.sport.sportsmallserver.exception.IdNotFoundException;
import com.sport.sportsmallserver.exception.NullFiledException;
import com.sport.sportsmallserver.exception.SecurityServerException;
import com.sport.sportsmallserver.repository.CommodityDetailRepository;
import com.sport.sportsmallserver.repository.CommodityRepository;
import com.sport.sportsmallserver.repository.CommodityTypeRepository;
import com.sport.sportsmallserver.service.CommodityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author itning
 * @date 2020/2/12 12:49
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommodityServiceImpl implements CommodityService {
    private static final String SECOND_IMG_SUFFIX = ";";

    private final CommodityRepository commodityRepository;
    private final CommodityTypeRepository commodityTypeRepository;
    private final CommodityDetailRepository commodityDetailRepository;

    @Autowired
    public CommodityServiceImpl(CommodityRepository commodityRepository, CommodityTypeRepository commodityTypeRepository, CommodityDetailRepository commodityDetailRepository) {
        this.commodityRepository = commodityRepository;
        this.commodityTypeRepository = commodityTypeRepository;
        this.commodityDetailRepository = commodityDetailRepository;
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
                if (commodity.getImgSecond().endsWith(SECOND_IMG_SUFFIX)) {
                    commodity.setImgSecond(commodity.getImgSecond().substring(0, commodity.getImgSecond().length() - 1));
                }
                String[] split = commodity.getImgSecond().split(SECOND_IMG_SUFFIX);
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

    @Override
    public Commodity add(String name, BigDecimal price, int stock, boolean recommended, String commodityTypeId, String imgMain, String imgSecond, String detail) {
        if (StringUtils.isAnyBlank(name, commodityTypeId, imgMain, imgSecond, detail)) {
            throw new NullFiledException("字段为空");
        }
        if (price.intValue() < 0) {
            throw new SecurityServerException("价格不能为负数", HttpStatus.BAD_REQUEST);
        }
        if (stock < 0) {
            throw new SecurityServerException("库存不能为负数", HttpStatus.BAD_REQUEST);
        }
        try {
            if (imgSecond.endsWith(SECOND_IMG_SUFFIX)) {
                imgSecond = imgSecond.substring(0, imgSecond.length() - 1);
            }
            String[] split = imgSecond.split(SECOND_IMG_SUFFIX);
            System.out.println(Arrays.toString(split));
        } catch (Exception e) {
            throw new SecurityServerException("副图片格式错误", HttpStatus.BAD_REQUEST);
        }
        CommodityType commodityType = commodityTypeRepository.findById(commodityTypeId).orElseThrow(() -> new IdNotFoundException("商品分类不存在"));
        Commodity commodity = new Commodity();
        commodity.setName(name);
        commodity.setStock(stock);
        commodity.setSales(0);
        commodity.setPrice(price);
        commodity.setImgMain(imgMain);
        commodity.setImgSecond(imgSecond);
        commodity.setRecommended(recommended);
        commodity.setTakeOff(false);
        commodity.setCommodityType(commodityType);
        Commodity saved = commodityRepository.save(commodity);

        CommodityDetail commodityDetail = new CommodityDetail();
        commodityDetail.setCommodity(saved);
        commodityDetail.setDetail(detail);
        commodityDetailRepository.save(commodityDetail);
        return saved;
    }
}
