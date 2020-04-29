package com.sport.sportsmallserver.repository;

import com.sport.sportsmallserver.entity.Comment;
import com.sport.sportsmallserver.entity.Commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author itning
 * @date 2020/2/12 10:36
 */
public interface CommentRepository extends JpaRepository<Comment, String> {
    /**
     * 根据商品查询评价
     *
     * @param commodity 商品
     * @param pageable  分页
     * @return 评价集合
     */
    Page<Comment> findByCommodity(Commodity commodity, Pageable pageable);
}
