package com.daema.core.scm.repository.util;

import com.daema.core.scm.domain.payment.ApplicationPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<ApplicationPayment, Long> {

}

