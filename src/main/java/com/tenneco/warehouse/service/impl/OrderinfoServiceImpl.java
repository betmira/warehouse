package com.tenneco.warehouse.service.impl;

import com.tenneco.warehouse.service.OrderinfoService;
import com.tenneco.warehouse.domain.Orderinfo;
import com.tenneco.warehouse.repository.OrderinfoRepository;
import com.tenneco.warehouse.web.rest.dto.OrderinfoDTO;
import com.tenneco.warehouse.web.rest.mapper.OrderinfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Orderinfo.
 */
@Service
@Transactional
public class OrderinfoServiceImpl implements OrderinfoService{

    private final Logger log = LoggerFactory.getLogger(OrderinfoServiceImpl.class);
    
    @Inject
    private OrderinfoRepository orderinfoRepository;
    
    @Inject
    private OrderinfoMapper orderinfoMapper;
    
    /**
     * Save a orderinfo.
     * @return the persisted entity
     */
    public OrderinfoDTO save(OrderinfoDTO orderinfoDTO) {
        log.debug("Request to save Orderinfo : {}", orderinfoDTO);
        Orderinfo orderinfo = orderinfoMapper.orderinfoDTOToOrderinfo(orderinfoDTO);
        orderinfo = orderinfoRepository.save(orderinfo);
        OrderinfoDTO result = orderinfoMapper.orderinfoToOrderinfoDTO(orderinfo);
        return result;
    }

    /**
     *  get all the orderinfos.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Orderinfo> findAll(Pageable pageable) {
        log.debug("Request to get all Orderinfos");
        Page<Orderinfo> result = orderinfoRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one orderinfo by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OrderinfoDTO findOne(Long id) {
        log.debug("Request to get Orderinfo : {}", id);
        Orderinfo orderinfo = orderinfoRepository.findOne(id);
        OrderinfoDTO orderinfoDTO = orderinfoMapper.orderinfoToOrderinfoDTO(orderinfo);
        return orderinfoDTO;
    }

    /**
     *  delete the  orderinfo by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Orderinfo : {}", id);
        orderinfoRepository.delete(id);
    }
}
