package com.sport.sportsmallserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sport.sportsmallserver.dto.LoginUser;
import com.sport.sportsmallserver.dto.RestModel;
import com.sport.sportsmallserver.entity.User;
import com.sport.sportsmallserver.security.MustUserLogin;
import com.sport.sportsmallserver.service.UserService;
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
     * 修改用户信息（不包括密码）
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

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param email    邮箱
     * @param phone    手机号
     * @param password 密码
     * @return ResponseEntity
     */
    @PostMapping("/reg")
    public ResponseEntity<?> regUser(@RequestParam String username,
                                     @RequestParam String email,
                                     @RequestParam String phone,
                                     @RequestParam String password) {
        return RestModel.created(userService.reg(username, email, phone, password));
    }

    /**
     * 更改密码
     *
     * @param loginUser 登录用户
     * @param oldPwd    旧密码
     * @param newPwd    新密码
     * @return ResponseEntity
     */
    @PostMapping("/pwd")
    public ResponseEntity<?> changePwd(@MustUserLogin LoginUser loginUser,
                                       @RequestParam String oldPwd,
                                       @RequestParam String newPwd) {
        userService.changePwd(loginUser, oldPwd, newPwd);
        return RestModel.ok("success");
    }
}
