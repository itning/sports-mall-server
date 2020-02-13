package com.sport.sportsmailserver.service;

import com.sport.sportsmailserver.entity.Carousel;

import java.util.List;

/**
 * 走马灯服务
 *
 * @author itning
 * @date 2020/2/13 12:39
 */
public interface CarouselService {
    /**
     * 获取所有走马灯列表
     *
     * @param type 类型
     * @return 走马灯集合
     */
    List<Carousel> getAll(int type);
}
