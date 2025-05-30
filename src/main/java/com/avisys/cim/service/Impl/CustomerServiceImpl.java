package com.avisys.cim.service.Impl;

import com.avisys.cim.Customer;
import com.avisys.cim.dto.CustomerDto;
import com.avisys.cim.repository.CustomerRepository;
import com.avisys.cim.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public List<CustomerDto> getAllCustomersByFilters(String firstName, String lastName, String mobileNumber) {
        List<Customer> customersByFilters = customerRepository.findCustomersByFilters(firstName, lastName, mobileNumber);

        return customersByFilters.stream().map(customers -> objectMapper.convertValue(customers,CustomerDto.class))
                .collect(Collectors.toList());
    }
}
