package com.sport.sportsmallserver.controller;

import com.sport.sportsmallserver.dto.LoginUser;
import com.sport.sportsmallserver.dto.RestModel;
import com.sport.sportsmallserver.entity.CommodityDetail;
import com.sport.sportsmallserver.security.MustAdminLogin;
import com.sport.sportsmallserver.security.MustLogin;
import com.sport.sportsmallserver.service.CommodityDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> findByCommodityId(@MustLogin(role = {MustLogin.ROLE.ADMIN, MustLogin.ROLE.USER}) LoginUser loginUser,
                                               @PathVariable String commodityId) {
        return RestModel.ok(commodityDetailService.findByCommodityId(commodityId));
    }

    /**
     * 修改商品详情或新增
     *
     * @param loginUser       登录用户
     * @param commodityDetail 商品详情
     * @return ResponseEntity
     */
    @PatchMapping("/commodityDetail")
    public ResponseEntity<?> modifyOrAdd(@MustAdminLogin LoginUser loginUser,
                                         @RequestBody CommodityDetail commodityDetail) {
        commodityDetailService.modifyOrAdd(commodityDetail);
        return RestModel.noContent();
    }
}
