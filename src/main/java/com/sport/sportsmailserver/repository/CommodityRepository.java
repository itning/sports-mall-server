package com.sport.sportsmailserver.repository;

import com.sport.sportsmailserver.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author itning
 * @date 2020/2/11 20:17
 */
public interface CommodityRepository extends JpaRepository<Commodity, String> {
}
