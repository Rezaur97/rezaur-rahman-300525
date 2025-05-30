package com.avisys.cim.controller;

import com.avisys.cim.dto.CustomerDto;
import com.avisys.cim.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getCustomers(@RequestParam(required = false) String firstName,
                                                          @RequestParam(required = false) String lastName,
                                                          @RequestParam(required = false) String mobileNumber){
        List<CustomerDto> allCustomers = customerService.getAllCustomersByFilters(firstName, lastName, mobileNumber);
        return ResponseEntity.ok(allCustomers);
    }
}
