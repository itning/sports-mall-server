package com.sport.sportsmailserver.service.impl;

import com.sport.sportsmailserver.dto.CommentDTO;
import com.sport.sportsmailserver.dto.LoginUser;
import com.sport.sportsmailserver.entity.Comment;
import com.sport.sportsmailserver.entity.Commodity;
import com.sport.sportsmailserver.entity.Order;
import com.sport.sportsmailserver.entity.User;
import com.sport.sportsmailserver.exception.IdNotFoundException;
import com.sport.sportsmailserver.exception.NullFiledException;
import com.sport.sportsmailserver.exception.SecurityServerException;
import com.sport.sportsmailserver.repository.CommentRepository;
import com.sport.sportsmailserver.repository.OrderRepository;
import com.sport.sportsmailserver.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author itning
 * @date 2020/2/12 15:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, OrderRepository orderRepository) {
        this.commentRepository = commentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Page<CommentDTO> findByCommodityId(String commodityId, Pageable pageable) {
        Commodity commodity = new Commodity();
        commodity.setId(commodityId);
        return commentRepository.findByCommodity(commodity, pageable)
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setId(comment.getId());
                    commentDTO.setUsername(generateAnonymousUserName(comment.getUser().getUsername()));
                    commentDTO.setContent(comment.getContent());
                    commentDTO.setGmtCreate(comment.getGmtCreate());
                    commentDTO.setGmtModified(comment.getGmtModified());
                    return commentDTO;
                });
    }

    @Override
    public Comment newComment(LoginUser loginUser, String orderId, String content) {
        if (StringUtils.isBlank(content)) {
            throw new NullFiledException("评价内容不能为空");
        }
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IdNotFoundException("订单不存在"));
        if (!order.getUser().getUsername().equals(loginUser.getUsername())) {
            throw new SecurityServerException("操作失败", HttpStatus.FORBIDDEN);
        }
        if (order.getStatus() != Order.STATUS.RECEIPT.getStatus()) {
            throw new SecurityServerException("重复评论或被删除", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(loginUser.getUsername());

        order.setStatus(Order.STATUS.EVALUATION.getStatus());
        Comment comment = new Comment();
        comment.setCommodity(order.getCommodity());
        comment.setUser(user);
        comment.setContent(content);

        orderRepository.save(order);
        return commentRepository.save(comment);
    }

    private String generateAnonymousUserName(String username) {
        if (username.length() < 2) {
            username += "**";
        }
        return username.substring(0, 2) + "***";
    }
}
