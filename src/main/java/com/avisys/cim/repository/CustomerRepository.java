package com.avisys.cim.repository;

import com.avisys.cim.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.mobileNumbers m " +
            "WHERE (:firstName IS NULL OR LOWER(c.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) " +
            "AND (:lastName IS NULL OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) " +
            "AND (:mobileNumber IS NULL OR m.mobileNumber = :mobileNumber)")
    List<Customer> findCustomersByFilters(@Param("firstName") String firstName,
                                          @Param("lastName") String lastName,
                                          @Param("mobileNumber") String mobileNumber);
}
