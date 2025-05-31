package com.avisys.cim.service.Impl;

import com.avisys.cim.Customer;
import com.avisys.cim.dto.CustomerDto;
import com.avisys.cim.exception.MobileNumberAlreadyExistsException;
import com.avisys.cim.repository.CustomerRepository;
import com.avisys.cim.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customerToSave = convertToEntity(customerDto);

        Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customerToSave.getMobileNumber());

        if (existingCustomer.isPresent()) {
            throw new MobileNumberAlreadyExistsException("Unable to create Customer. Mobile number already present.");
        }

        Customer savedCustomer = customerRepository.save(customerToSave);
        return convertToDto(savedCustomer);
    }

    private CustomerDto convertToDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setMobileNumber(customer.getMobileNumber());
        return dto;
    }


    private Customer convertToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
