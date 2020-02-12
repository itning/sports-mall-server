package com.sport.sportsmailserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;

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
}
