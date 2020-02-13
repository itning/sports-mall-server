package com.sport.sportsmailserver.controller;

import com.sport.sportsmailserver.dto.RestModel;
import com.sport.sportsmailserver.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "1") int type) {
        return RestModel.ok(carouselService.getAll(type));
    }
}
