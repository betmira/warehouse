package com.tenneco.warehouse.repository;

import com.tenneco.warehouse.domain.Orderinfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Orderinfo entity.
 */
public interface OrderinfoRepository extends JpaRepository<Orderinfo,Long> {

}
