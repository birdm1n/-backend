package com.daema.core.sms.repository;

import com.daema.core.sms.domain.Customer;
import com.daema.core.sms.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
