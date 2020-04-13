package com.sport.sportsmailserver.controller;

import com.sport.sportsmailserver.dto.CarouselDTO;
import com.sport.sportsmailserver.dto.LoginUser;
import com.sport.sportsmailserver.dto.RestModel;
import com.sport.sportsmailserver.security.MustAdminLogin;
import com.sport.sportsmailserver.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author itning
 * @date 2020/2/13 12:47
 */
@RestController
public class CarouselController {
    private final CarouselService carouselService;

    @Autowired
    public CarouselController(CarouselService carouselService) {
        this.carouselService = carouselService;
    }

    /**
     * 获取所有轮播图
     *
     * @param type 类型
     * @return ResponseEntity
     */
    @GetMapping("/carousels")
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "-1") int type) {
        return RestModel.ok(carouselService.getAll(type));
    }

    /**
     * 添加走马灯
     *
     * @param loginUser 登录用户
     * @param url       URL
     * @param link      链接
     * @param type      类型
     * @return ResponseEntity
     */
    @PostMapping("/carousel")
    public ResponseEntity<?> add(@MustAdminLogin LoginUser loginUser,
                                 @RequestParam String url,
                                 @RequestParam String link,
                                 @RequestParam int type) {
        return RestModel.created(carouselService.add(url, link, type));
    }

    /**
     * 删除走马灯
     *
     * @param loginUser 登录用户
     * @param id        ID
     * @return ResponseEntity
     */
    @DeleteMapping("/carousel/{id}")
    public ResponseEntity<?> del(@MustAdminLogin LoginUser loginUser,
                                 @PathVariable String id) {
        carouselService.del(id);
        return RestModel.noContent();
    }

    /**
     * 修改走马灯
     *
     * @param loginUser 登录用户
     * @param carousel  走马灯
     * @return ResponseEntity
     */
    @PatchMapping("/carousel")
    public ResponseEntity<?> modify(@MustAdminLogin LoginUser loginUser,
                                    @RequestBody CarouselDTO carousel) {
        carouselService.modify(carousel);
        return RestModel.noContent();
    }
}
