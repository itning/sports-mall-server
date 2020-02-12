package com.sport.sportsmailserver.service.impl;

import com.sport.sportsmailserver.dto.CommentDTO;
import com.sport.sportsmailserver.entity.Commodity;
import com.sport.sportsmailserver.repository.CommentRepository;
import com.sport.sportsmailserver.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
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

    private String generateAnonymousUserName(String username) {
        if (username.length() < 2) {
            username += "**";
        }
        return username.substring(0, 2) + "***";
    }
}
