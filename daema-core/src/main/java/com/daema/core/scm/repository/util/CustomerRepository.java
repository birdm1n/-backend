package com.daema.core.scm.repository.util;

import com.daema.core.scm.domain.customer.ApplicationCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<ApplicationCustomer, Long> {
}
