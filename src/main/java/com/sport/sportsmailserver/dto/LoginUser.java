package com.sport.sportsmailserver.dto;

import com.sport.sportsmailserver.entity.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录用户
 *
 * @author itning
 * @date 2020/2/12 11:07
 * @see com.sport.sportsmailserver.entity.User
 */
@Data
public class LoginUser implements Serializable {
    /**
     * 用户名（唯一）
     */
    private String username;
    /**
     * 邮箱（唯一）
     */
    private String email;
    /**
     * 电话
     */
    private String tel;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 所属角色
     */
    private Role role;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;
}
