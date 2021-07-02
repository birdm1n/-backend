package com.daema.core.sms.repository;

import com.daema.core.sms.domain.Customer;
import com.daema.core.sms.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
