package com.tenneco.warehouse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tenneco.warehouse.domain.Orderinfo;
import com.tenneco.warehouse.service.OrderinfoService;
import com.tenneco.warehouse.web.rest.util.HeaderUtil;
import com.tenneco.warehouse.web.rest.util.PaginationUtil;
import com.tenneco.warehouse.web.rest.dto.OrderinfoDTO;
import com.tenneco.warehouse.web.rest.mapper.OrderinfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Orderinfo.
 */
@RestController
@RequestMapping("/api")
public class OrderinfoResource {

    private final Logger log = LoggerFactory.getLogger(OrderinfoResource.class);
        
    @Inject
    private OrderinfoService orderinfoService;
    
    @Inject
    private OrderinfoMapper orderinfoMapper;
    
    /**
     * POST  /orderinfos -> Create a new orderinfo.
     */
    @RequestMapping(value = "/orderinfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderinfoDTO> createOrderinfo(@RequestBody OrderinfoDTO orderinfoDTO) throws URISyntaxException {
        log.debug("REST request to save Orderinfo : {}", orderinfoDTO);
        if (orderinfoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("orderinfo", "idexists", "A new orderinfo cannot already have an ID")).body(null);
        }
        OrderinfoDTO result = orderinfoService.save(orderinfoDTO);
        return ResponseEntity.created(new URI("/api/orderinfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("orderinfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orderinfos -> Updates an existing orderinfo.
     */
    @RequestMapping(value = "/orderinfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderinfoDTO> updateOrderinfo(@RequestBody OrderinfoDTO orderinfoDTO) throws URISyntaxException {
        log.debug("REST request to update Orderinfo : {}", orderinfoDTO);
        if (orderinfoDTO.getId() == null) {
            return createOrderinfo(orderinfoDTO);
        }
        OrderinfoDTO result = orderinfoService.save(orderinfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("orderinfo", orderinfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orderinfos -> get all the orderinfos.
     */
    @RequestMapping(value = "/orderinfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<OrderinfoDTO>> getAllOrderinfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Orderinfos");
        Page<Orderinfo> page = orderinfoService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orderinfos");
        return new ResponseEntity<>(page.getContent().stream()
            .map(orderinfoMapper::orderinfoToOrderinfoDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /orderinfos/:id -> get the "id" orderinfo.
     */
    @RequestMapping(value = "/orderinfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderinfoDTO> getOrderinfo(@PathVariable Long id) {
        log.debug("REST request to get Orderinfo : {}", id);
        OrderinfoDTO orderinfoDTO = orderinfoService.findOne(id);
        return Optional.ofNullable(orderinfoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orderinfos/:id -> delete the "id" orderinfo.
     */
    @RequestMapping(value = "/orderinfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOrderinfo(@PathVariable Long id) {
        log.debug("REST request to delete Orderinfo : {}", id);
        orderinfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("orderinfo", id.toString())).build();
    }
}
