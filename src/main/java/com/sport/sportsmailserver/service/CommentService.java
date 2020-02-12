package com.sport.sportsmailserver.service;

import com.sport.sportsmailserver.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 评论服务
 *
 * @author itning
 * @date 2020/2/12 15:52
 */
public interface CommentService {
    /**
     * 根据商品ID查找评论
     *
     * @param commodityId 商品ID
     * @param pageable    分页
     * @return 评论集合
     */
    Page<CommentDTO> findByCommodityId(String commodityId, Pageable pageable);
}
