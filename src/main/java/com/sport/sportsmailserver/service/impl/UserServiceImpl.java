package com.sport.sportsmailserver.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sport.sportsmailserver.dto.LoginUser;
import com.sport.sportsmailserver.entity.Role;
import com.sport.sportsmailserver.entity.User;
import com.sport.sportsmailserver.exception.IdNotFoundException;
import com.sport.sportsmailserver.exception.NullFiledException;
import com.sport.sportsmailserver.exception.SecurityServerException;
import com.sport.sportsmailserver.exception.TokenException;
import com.sport.sportsmailserver.repository.RoleRepository;
import com.sport.sportsmailserver.repository.UserRepository;
import com.sport.sportsmailserver.service.UserService;
import com.sport.sportsmailserver.util.JwtUtils;
import com.sport.sportsmailserver.util.OrikaUtils;
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
        if (StringUtils.isNotBlank(user.getPassword())) {
            savedUser.setPassword(user.getPassword());
        }
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
}
