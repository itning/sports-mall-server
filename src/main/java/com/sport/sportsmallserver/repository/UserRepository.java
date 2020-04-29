package com.sport.sportsmallserver.repository;

import com.sport.sportsmallserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author itning
 * @date 2020/2/11 20:17
 */
public interface UserRepository extends JpaRepository<User, String> {
    /**
     * 查询邮箱是否存在
     *
     * @param email 邮箱
     * @return 存在?
     */
    boolean existsByEmail(String email);
}
