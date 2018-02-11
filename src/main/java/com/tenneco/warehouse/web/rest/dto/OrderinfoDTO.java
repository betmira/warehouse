package com.tenneco.warehouse.web.rest.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Orderinfo entity.
 */
public class OrderinfoDTO implements Serializable {

    private Long id;

    private ZonedDateTime datecreate;


    private Long statusId;
    private Long customerId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(ZonedDateTime datecreate) {
        this.datecreate = datecreate;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderinfoDTO orderinfoDTO = (OrderinfoDTO) o;

        if ( ! Objects.equals(id, orderinfoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderinfoDTO{" +
            "id=" + id +
            ", datecreate='" + datecreate + "'" +
            '}';
    }
}
