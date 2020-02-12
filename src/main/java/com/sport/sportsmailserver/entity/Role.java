package com.sport.sportsmailserver.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 *
 * @author itning
 * @date 2020/2/12 10:53
 * @see com.sport.sportsmailserver.repository.RoleRepository
 * @see java.io.Serializable
 */
@Data
@Entity(name = "mail_role")
public class Role implements Serializable {
    /**
     * 管理员ID
     */
    private static final String ROLE_ADMIN_ID = "1";
    /**
     * 用户ID
     */
    private static final String ROLE_USER_ID = "2";
    /**
     * ID
     */
    @Id
    @Column(columnDefinition = "char(1)")
    private String id;
    /**
     * 角色名
     */
    @Column(nullable = false, unique = true)
    private String name;
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
