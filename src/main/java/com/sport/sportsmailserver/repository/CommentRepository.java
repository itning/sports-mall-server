package com.sport.sportsmailserver.repository;

import com.sport.sportsmailserver.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author itning
 * @date 2020/2/12 10:36
 */
public interface CommentRepository extends JpaRepository<Comment, String> {
}
