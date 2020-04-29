package com.sport.sportsmallserver.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论信息DTO
 *
 * @author itning
 * @date 2020/2/12 15:55
 */
@Data
public class CommentDTO implements Serializable {
    /**
     * ID
     */
    private String id;
    /**
     * 用户
     */
    private String username;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;
}
