package com.tenneco.warehouse.service;

import com.tenneco.warehouse.domain.Orderinfo;
import com.tenneco.warehouse.web.rest.dto.OrderinfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Orderinfo.
 */
public interface OrderinfoService {

    /**
     * Save a orderinfo.
     * @return the persisted entity
     */
    public OrderinfoDTO save(OrderinfoDTO orderinfoDTO);

    /**
     *  get all the orderinfos.
     *  @return the list of entities
     */
    public Page<Orderinfo> findAll(Pageable pageable);

    /**
     *  get the "id" orderinfo.
     *  @return the entity
     */
    public OrderinfoDTO findOne(Long id);

    /**
     *  delete the "id" orderinfo.
     */
    public void delete(Long id);
}
