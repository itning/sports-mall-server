package com.sport.sportsmallserver.security;

import java.lang.annotation.*;

/**
 * 表示登录后才可以进行访问
 *
 * @author itning
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MustLogin {
    /**
     * 登录角色
     *
     * @return 角色数组
     */
    ROLE[] role();

    enum ROLE {
        /**
         * 管理员
         */
        ADMIN("1", "管理员"),
        /**
         * 用户
         */
        USER("2", "用户");

        private final String id;
        private final String name;

        ROLE(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "ROLE{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
