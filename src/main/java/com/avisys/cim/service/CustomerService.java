package com.avisys.cim.service;

import com.avisys.cim.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> getAllCustomersByFilters(String firstName, String lastName, String mobileNumber);

    CustomerDto createCustomer(CustomerDto customerDto);

    void deleteCustomerByMobile(String mobileNumber);

    void addMobileNumber(Long customerId, String mobileNumber);

    void deleteMobileNumber(String mobileNumber);
}
