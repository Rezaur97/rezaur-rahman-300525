package com.avisys.cim.repository;

import com.avisys.cim.entity.MobileNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MobileNumberRepository extends JpaRepository<MobileNumber,Long> {

    List<MobileNumber> findByMobileNumberIn(List<String> numbers);

    Optional<MobileNumber> findByMobileNumber(String mobileNumber);

}
