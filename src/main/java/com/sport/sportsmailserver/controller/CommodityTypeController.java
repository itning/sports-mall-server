package com.sport.sportsmailserver.controller;

import com.sport.sportsmailserver.dto.RestModel;
import com.sport.sportsmailserver.service.CommodityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author itning
 * @date 2020/2/12 12:26
 */
@RestController
public class CommodityTypeController {
    private final CommodityTypeService commodityTypeService;

    @Autowired
    public CommodityTypeController(CommodityTypeService commodityTypeService) {
        this.commodityTypeService = commodityTypeService;
    }

    /**
     * 获取所有商品分类
     *
     * @return ResponseEntity
     */
    @GetMapping("/commodity_types")
    public ResponseEntity<?> getAllCommodityType() {
        return RestModel.ok(commodityTypeService.getAll());
    }
}
