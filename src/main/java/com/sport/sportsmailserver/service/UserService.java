package com.sport.sportsmailserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sport.sportsmailserver.dto.LoginUser;
import com.sport.sportsmailserver.entity.User;

/**
 * 用户服务
 *
 * @author itning
 * @date 2020/2/12 11:29
 */
public interface UserService {
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return jwt token
     * @throws JsonProcessingException JWT构建失败
     */
    String login(String username, String password) throws JsonProcessingException;

    /**
     * 修改用户信息
     *
     * @param loginUser 登录用户
     * @param user      新用户信息
     */
    void modifyUser(LoginUser loginUser, User user);
}
