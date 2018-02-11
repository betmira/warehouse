package com.tenneco.warehouse.service;

import com.tenneco.warehouse.domain.Customer;
import com.tenneco.warehouse.web.rest.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Customer.
 */
public interface CustomerService {

    /**
     * Save a customer.
     * @return the persisted entity
     */
    public CustomerDTO save(CustomerDTO customerDTO);

    /**
     *  get all the customers.
     *  @return the list of entities
     */
    public Page<Customer> findAll(Pageable pageable);

    /**
     *  get the "id" customer.
     *  @return the entity
     */
    public CustomerDTO findOne(Long id);

    /**
     *  delete the "id" customer.
     */
    public void delete(Long id);
}
