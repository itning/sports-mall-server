package com.sport.sportsmailserver.service.impl;

import com.sport.sportsmailserver.dto.CarouselDTO;
import com.sport.sportsmailserver.entity.Carousel;
import com.sport.sportsmailserver.exception.IdNotFoundException;
import com.sport.sportsmailserver.exception.NullFiledException;
import com.sport.sportsmailserver.repository.CarouselRepository;
import com.sport.sportsmailserver.service.CarouselService;
import org.apache.commons.lang3.StringUtils;
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
        if (-1 == type) {
            return carouselRepository.findAll();
        }
        return carouselRepository.findAllByType(type);
    }

    @Override
    public Carousel add(String url, String link, int type) {
        if (StringUtils.isAnyBlank(url, link)) {
            throw new NullFiledException("URL或链接为空");
        }
        Carousel carousel = new Carousel();
        carousel.setUrl(url);
        carousel.setType(type);
        carousel.setLink(link);
        return carouselRepository.save(carousel);
    }

    @Override
    public void del(String id) {
        carouselRepository.deleteById(id);
    }

    @Override
    public void modify(CarouselDTO carousel) {
        if (StringUtils.isBlank(carousel.getId())) {
            throw new NullFiledException("ID为空");
        }
        Carousel saved = carouselRepository.findById(carousel.getId()).orElseThrow(() -> new IdNotFoundException("ID不存在"));
        if (!saved.getLink().equals(carousel.getLink()) && carousel.getLink() != null) {
            saved.setLink(carousel.getLink());
        }
        if (!saved.getUrl().equals(carousel.getUrl()) && carousel.getUrl() != null) {
            saved.setUrl(carousel.getUrl());
        }
        if (carousel.getType() != null && carousel.getType() != saved.getType()) {
            saved.setType(carousel.getType());
        }
        carouselRepository.save(saved);
    }
}
