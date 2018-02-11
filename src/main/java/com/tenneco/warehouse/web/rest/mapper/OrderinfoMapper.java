package com.tenneco.warehouse.web.rest.mapper;

import com.tenneco.warehouse.domain.*;
import com.tenneco.warehouse.web.rest.dto.OrderinfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Orderinfo and its DTO OrderinfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderinfoMapper {

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "customer.id", target = "customerId")
    OrderinfoDTO orderinfoToOrderinfoDTO(Orderinfo orderinfo);

    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "customerId", target = "customer")
    Orderinfo orderinfoDTOToOrderinfo(OrderinfoDTO orderinfoDTO);

    default Status statusFromId(Long id) {
        if (id == null) {
            return null;
        }
        Status status = new Status();
        status.setId(id);
        return status;
    }

    default Customer customerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
