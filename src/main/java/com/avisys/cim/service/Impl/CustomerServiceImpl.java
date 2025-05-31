package com.avisys.cim.service.Impl;

import com.avisys.cim.dto.MobileNumberDto;
import com.avisys.cim.entity.Customer;
import com.avisys.cim.dto.CustomerDto;
import com.avisys.cim.entity.MobileNumber;
import com.avisys.cim.exception.MobileNumberAlreadyExistsException;
import com.avisys.cim.repository.CustomerRepository;
import com.avisys.cim.repository.MobileNumberRepository;
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
    private MobileNumberRepository mobileNumberRepository;

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

        List<String> numbers = customerDto.getMobileNumbers()
                .stream().map(MobileNumberDto::getMobileNumber)
                .collect(Collectors.toList());

        List<MobileNumber> existingNumbers = mobileNumberRepository.findByMobileNumberIn(numbers);

        if (!existingNumbers.isEmpty()) {
            throw new MobileNumberAlreadyExistsException("Unable to create Customer. Mobile number(s) already present - "
                    + existingNumbers.stream().map(MobileNumber::getMobileNumber).collect(Collectors.joining(", ")));
        }

        Customer customerToSave = convertToEntity(customerDto);

        Customer savedCustomer = customerRepository.save(customerToSave);
        return convertToDto(savedCustomer);
    }

    private CustomerDto convertToDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setMobileNumbers(customer.getMobileNumbers().stream()
                .map(m -> {
                    MobileNumberDto mnDto = new MobileNumberDto();
                    mnDto.setMobileNumber(m.getMobileNumber());
                    return mnDto;
                })
                .collect(Collectors.toList()));
        return dto;
    }



    private Customer convertToEntity(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());

        List<MobileNumber> mobileNumbers = dto.getMobileNumbers().stream()
                .map(num -> {
                    MobileNumber mn = new MobileNumber();
                    mn.setMobileNumber(num.getMobileNumber());
                    mn.setCustomer(customer); // set back-reference
                    return mn;
                })
                .collect(Collectors.toList());

        customer.setMobileNumbers(mobileNumbers);
        return customer;
    }

}
