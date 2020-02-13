package com.sport.sportsmailserver.service;

import com.sport.sportsmailserver.dto.CarouselDTO;
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

    /**
     * 添加走马灯
     *
     * @param url  网址
     * @param link 链接
     * @param type 类型
     * @return 新增的走马灯
     */
    Carousel add(String url, String link, int type);

    /**
     * 删除走马灯
     *
     * @param id ID
     */
    void del(String id);

    /**
     * 修改走马灯
     *
     * @param carousel 走马灯
     */
    void modify(CarouselDTO carousel);
}
