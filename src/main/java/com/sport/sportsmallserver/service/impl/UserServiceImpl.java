package com.sport.sportsmallserver.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sport.sportsmallserver.dto.LoginUser;
import com.sport.sportsmallserver.entity.Role;
import com.sport.sportsmallserver.entity.User;
import com.sport.sportsmallserver.exception.IdNotFoundException;
import com.sport.sportsmallserver.exception.NullFiledException;
import com.sport.sportsmallserver.exception.SecurityServerException;
import com.sport.sportsmallserver.exception.TokenException;
import com.sport.sportsmallserver.repository.RoleRepository;
import com.sport.sportsmallserver.repository.UserRepository;
import com.sport.sportsmallserver.service.UserService;
import com.sport.sportsmallserver.util.JwtUtils;
import com.sport.sportsmallserver.util.OrikaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author itning
 * @date 2020/2/12 11:31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        if (!roleRepository.existsById(Role.ROLE_ADMIN_ID)) {
            Role role = new Role();
            role.setId(Role.ROLE_ADMIN_ID);
            role.setName("管理员");
            roleRepository.save(role);
        }
        if (!roleRepository.existsById(Role.ROLE_USER_ID)) {
            Role role = new Role();
            role.setId(Role.ROLE_USER_ID);
            role.setName("用户");
            roleRepository.save(role);
        }
    }

    @Override
    public String login(String username, String password) throws JsonProcessingException {
        if (StringUtils.isAnyBlank(username, password)) {
            throw new NullFiledException("用户名/密码不能为空");
        }
        User user = userRepository.findById(username).orElseThrow(() -> new IdNotFoundException("用户名不存在"));
        if (!password.equals(user.getPassword())) {
            throw new SecurityServerException("密码错误", HttpStatus.BAD_REQUEST);
        }
        LoginUser loginUser = OrikaUtils.a2b(user, LoginUser.class);
        return JwtUtils.buildJwt(loginUser);
    }

    @Override
    public void modifyUser(LoginUser loginUser, User user) {
        User savedUser = userRepository.findById(loginUser.getUsername()).orElseThrow(() -> new TokenException("用户不存在", HttpStatus.BAD_REQUEST));
        if (StringUtils.isNotBlank(user.getAddress())) {
            savedUser.setAddress(user.getAddress());
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new NullFiledException("该邮箱已经被注册");
            }
            savedUser.setEmail(user.getEmail());
        }
        if (StringUtils.isNotBlank(user.getTel())) {
            savedUser.setTel(user.getTel());
        }
        userRepository.save(savedUser);
    }

    @Override
    public LoginUser reg(String username, String email, String phone, String password) {
        if (StringUtils.isAnyBlank(username, email, phone, password)) {
            throw new NullFiledException("某些字段为空");
        }
        if (userRepository.existsByEmail(email)) {
            throw new SecurityServerException("邮箱已被注册", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsById(username)) {
            throw new SecurityServerException("用户名已被注册", HttpStatus.BAD_REQUEST);
        }
        Role role = new Role();
        role.setId(Role.ROLE_USER_ID);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setTel(phone);
        user.setRole(role);
        User savedUser = userRepository.save(user);
        return OrikaUtils.a2b(savedUser, LoginUser.class);
    }

    @Override
    public void changePwd(LoginUser loginUser, String oldPwd, String newPwd) {
        if (StringUtils.isAnyBlank(oldPwd, newPwd)) {
            throw new NullFiledException("密码不能为空");
        }
        User user = userRepository.findById(loginUser.getUsername()).orElseThrow(() -> new TokenException("用户不存在", HttpStatus.BAD_REQUEST));
        if (!oldPwd.equals(user.getPassword())) {
            throw new SecurityServerException("密码错误", HttpStatus.BAD_REQUEST);
        }
        if (newPwd.equals(user.getPassword())) {
            throw new SecurityServerException("新密码不能和旧密码相同", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(newPwd);
        userRepository.save(user);
    }
}
