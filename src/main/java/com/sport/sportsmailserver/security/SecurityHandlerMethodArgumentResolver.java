package com.sport.sportsmailserver.security;

import com.sport.sportsmailserver.dto.LoginUser;
import com.sport.sportsmailserver.entity.Role;
import com.sport.sportsmailserver.exception.SecurityServerException;
import com.sport.sportsmailserver.exception.TokenException;
import com.sport.sportsmailserver.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author itning
 */
@Component
public class SecurityHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(SecurityHandlerMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MustLogin.class) ||
                parameter.hasParameterAnnotation(MustAdminLogin.class) ||
                parameter.hasParameterAnnotation(MustUserLogin.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (null == authorizationHeader) {
            throw new TokenException("凭据错误", HttpStatus.BAD_REQUEST);
        }
        LoginUser loginUser = JwtUtils.getLoginUser(authorizationHeader);
        checkLoginPermission(parameter, loginUser.getRole().getId());
        return loginUser;
    }

    private void checkLoginPermission(@NonNull MethodParameter parameter, String roleId) {
        if (parameter.hasParameterAnnotation(MustUserLogin.class) &&
                !Role.ROLE_USER_ID.equals(roleId)) {
            logger.debug("MustUserLogin role id {}", roleId);
            throw new SecurityServerException("权限不足", HttpStatus.FORBIDDEN);
        }
        if (parameter.hasParameterAnnotation(MustAdminLogin.class) &&
                !Role.ROLE_ADMIN_ID.equals(roleId)) {
            logger.debug("MustAdminLogin role id {}", roleId);
            throw new SecurityServerException("权限不足", HttpStatus.FORBIDDEN);
        }
        if (parameter.hasParameterAnnotation(MustLogin.class)) {
            MustLogin mustLogin = parameter.getParameterAnnotation(MustLogin.class);
            if (mustLogin != null) {
                if (Arrays.stream(mustLogin.role()).noneMatch(role -> role.getId().equals(roleId))) {
                    logger.debug("MustLogin role id {} and set role array {}", roleId, Arrays.toString(mustLogin.role()));
                    throw new SecurityServerException("权限不足", HttpStatus.FORBIDDEN);
                }
            }
        }
    }
}
