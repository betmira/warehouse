package com.tenneco.warehouse.service.impl;

import com.tenneco.warehouse.service.CustomerService;
import com.tenneco.warehouse.domain.Customer;
import com.tenneco.warehouse.repository.CustomerRepository;
import com.tenneco.warehouse.web.rest.dto.CustomerDTO;
import com.tenneco.warehouse.web.rest.mapper.CustomerMapper;
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
 * Service Implementation for managing Customer.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    
    @Inject
    private CustomerRepository customerRepository;
    
    @Inject
    private CustomerMapper customerMapper;
    
    /**
     * Save a customer.
     * @return the persisted entity
     */
    public CustomerDTO save(CustomerDTO customerDTO) {
        log.debug("Request to save Customer : {}", customerDTO);
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer = customerRepository.save(customer);
        CustomerDTO result = customerMapper.customerToCustomerDTO(customer);
        return result;
    }

    /**
     *  get all the customers.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Customer> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        Page<Customer> result = customerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one customer by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CustomerDTO findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        Customer customer = customerRepository.findOne(id);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        return customerDTO;
    }

    /**
     *  delete the  customer by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.delete(id);
    }
}
