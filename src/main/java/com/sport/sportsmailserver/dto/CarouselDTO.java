package com.sport.sportsmailserver.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author itning
 * @date 2020/2/13 17:44
 */
@Data
public class CarouselDTO implements Serializable {
    /**
     * ID
     */
    private String id;
    /**
     * URL
     */
    private String url;
    /**
     * 轮播类型（默认水平）
     */
    private Integer type;
    /**
     * 链接
     */
    private String link;
}
