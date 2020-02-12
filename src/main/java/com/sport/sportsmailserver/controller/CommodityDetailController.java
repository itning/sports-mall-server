package com.sport.sportsmailserver.controller;

import com.sport.sportsmailserver.dto.LoginUser;
import com.sport.sportsmailserver.dto.RestModel;
import com.sport.sportsmailserver.security.MustUserLogin;
import com.sport.sportsmailserver.service.CommodityDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品详情
 *
 * @author itning
 * @date 2020/2/12 15:30
 */
@RestController
public class CommodityDetailController {
    private final CommodityDetailService commodityDetailService;

    @Autowired
    public CommodityDetailController(CommodityDetailService commodityDetailService) {
        this.commodityDetailService = commodityDetailService;
    }

    /**
     * 根据商品ID查询商品详情
     *
     * @param loginUser   登录用户
     * @param commodityId 商品ID
     * @return ResponseEntity
     */
    @GetMapping("/commodityDetail/{commodityId}")
    public ResponseEntity<?> findByCommodityId(@MustUserLogin LoginUser loginUser,
                                               @PathVariable String commodityId) {
        return RestModel.ok(commodityDetailService.findByCommodityId(commodityId));
    }
}
