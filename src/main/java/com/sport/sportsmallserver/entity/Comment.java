package com.sport.sportsmallserver.entity;

import com.sport.sportsmallserver.repository.CommentRepository;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>商品评价
 * <p>已添加商品ID，用户ID索引
 *
 * @author itning
 * @date 2020/2/12 10:29
 * @see CommentRepository
 * @see java.io.Serializable
 */
@Data
@Entity(name = "mail_comment")
@Table(indexes = {
        @Index(name = "idx_user_id", columnList = "userId"),
        @Index(name = "idx_commodity_id", columnList = "commodityId")
})
public class Comment implements Serializable {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "char(36)")
    private String id;
    /**
     * 商品
     */
    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne(optional = false)
    @JoinColumn(name = "commodityId", columnDefinition = "char(36)")
    private Commodity commodity;
    /**
     * 用户
     */
    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", columnDefinition = "varchar(255)")
    private User user;
    /**
     * 内容
     */
    @Column(nullable = false, columnDefinition = "text")
    private String content;
    /**
     * 创建时间
     */
    @Column(nullable = false)
    @CreationTimestamp
    private Date gmtCreate;
    /**
     * 更新时间
     */
    @Column(nullable = false)
    @UpdateTimestamp
    private Date gmtModified;
}
