package com.sport.sportsmallserver.repository;

import com.sport.sportsmallserver.entity.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author itning
 * @date 2020/2/13 12:38
 */
public interface CarouselRepository extends JpaRepository<Carousel, String> {
    /**
     * 根据类型获取轮播图
     *
     * @param type 类型
     * @return 轮播图集合
     */
    List<Carousel> findAllByType(int type);
}
