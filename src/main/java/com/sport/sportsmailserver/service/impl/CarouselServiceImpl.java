package com.sport.sportsmailserver.service.impl;

import com.sport.sportsmailserver.entity.Carousel;
import com.sport.sportsmailserver.repository.CarouselRepository;
import com.sport.sportsmailserver.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author itning
 * @date 2020/2/13 12:46
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CarouselServiceImpl implements CarouselService {
    private final CarouselRepository carouselRepository;

    @Autowired
    public CarouselServiceImpl(CarouselRepository carouselRepository) {
        this.carouselRepository = carouselRepository;
    }

    @Override
    public List<Carousel> getAll(int type) {
        return carouselRepository.findAllByType(type);
    }
}
