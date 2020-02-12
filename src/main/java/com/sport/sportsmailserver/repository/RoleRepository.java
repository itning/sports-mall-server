package com.sport.sportsmailserver.repository;

import com.sport.sportsmailserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author itning
 * @date 2020/2/12 10:54
 */
public interface RoleRepository extends JpaRepository<Role, String> {
}
