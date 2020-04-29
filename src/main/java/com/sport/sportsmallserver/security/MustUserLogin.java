package com.sport.sportsmallserver.security;

import java.lang.annotation.*;

/**
 * 必须是用户登录
 *
 * @author itning
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MustLogin(role = MustLogin.ROLE.USER)
public @interface MustUserLogin {
}
