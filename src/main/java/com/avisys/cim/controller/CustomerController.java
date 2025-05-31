package com.avisys.cim.controller;

import com.avisys.cim.dto.CustomerDto;
import com.avisys.cim.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto savedCustomer = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/by-mobile/{mobileNumber}")
    public ResponseEntity<String> deleteCustomerByMobile(@PathVariable String mobileNumber) {
        customerService.deleteCustomerByMobile(mobileNumber);
        return ResponseEntity.ok("Customer deleted successfully.");
    }

}
