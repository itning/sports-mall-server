package com.sport.sportsmailserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sport.sportsmailserver.dto.LoginUser;
import com.sport.sportsmailserver.dto.RestModel;
import com.sport.sportsmailserver.entity.User;
import com.sport.sportsmailserver.security.MustUserLogin;
import com.sport.sportsmailserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author itning
 * @date 2020/2/12 11:39
 */
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return ResponseEntity
     * @throws JsonProcessingException See {@link UserService#login(String, String)}
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) throws JsonProcessingException {
        return RestModel.ok(userService.login(username, password));
    }

    /**
     * 修改用户信息
     *
     * @param loginUser 登录用户
     * @param user      新用户信息
     * @return ResponseEntity
     */
    @PatchMapping("/user")
    public ResponseEntity<?> modifyUserInfo(@MustUserLogin LoginUser loginUser,
                                            @RequestBody User user) {
        userService.modifyUser(loginUser, user);
        return RestModel.noContent();
    }
}
