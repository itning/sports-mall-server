package com.sport.sportsmailserver.security;

import java.lang.annotation.*;

/**
 * 必须是管理员登录
 *
 * @author itning
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MustLogin(role = MustLogin.ROLE.ADMIN)
public @interface MustAdminLogin {
}
