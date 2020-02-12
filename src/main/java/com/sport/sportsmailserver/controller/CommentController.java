package com.sport.sportsmailserver.controller;

import com.sport.sportsmailserver.dto.LoginUser;
import com.sport.sportsmailserver.dto.RestModel;
import com.sport.sportsmailserver.security.MustUserLogin;
import com.sport.sportsmailserver.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author itning
 * @date 2020/2/12 15:50
 */
@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 获取商品评论
     *
     * @param loginUser   登录用户
     * @param commodityId 商品ID
     * @param pageable    分页
     * @return ResponseEntity
     */
    @GetMapping("/comment/{commodityId}")
    public ResponseEntity<?> findByCommodityId(@MustUserLogin LoginUser loginUser,
                                               @PathVariable String commodityId,
                                               @PageableDefault(
                                                       size = 20, sort = {"gmtModified"},
                                                       direction = Sort.Direction.DESC
                                               )
                                                       Pageable pageable) {
        return RestModel.ok(commentService.findByCommodityId(commodityId, pageable));
    }

    /**
     * 新评价
     *
     * @param loginUser 登录用户
     * @param orderId   订单ID
     * @param content   内容
     * @return ResponseEntity
     */
    @PostMapping("/comment")
    public ResponseEntity<?> newComment(@MustUserLogin LoginUser loginUser,
                                        @RequestParam String orderId,
                                        @RequestParam String content) {
        return RestModel.created(commentService.newComment(loginUser, orderId, content));
    }
}
