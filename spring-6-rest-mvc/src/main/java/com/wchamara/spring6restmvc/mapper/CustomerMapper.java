package com.wchamara.spring6restmvc.mapper;

import com.wchamara.spring6restmvc.entities.Customer;
import com.wchamara.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
