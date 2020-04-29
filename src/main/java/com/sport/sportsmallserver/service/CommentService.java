package com.sport.sportsmallserver.service;

import com.sport.sportsmallserver.dto.CommentDTO;
import com.sport.sportsmallserver.dto.LoginUser;
import com.sport.sportsmallserver.entity.Comment;
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

    /**
     * 新评论
     *
     * @param loginUser 登录用户
     * @param orderId   订单ID
     * @param content   内容
     * @return 评论
     */
    Comment newComment(LoginUser loginUser, String orderId, String content);
}
