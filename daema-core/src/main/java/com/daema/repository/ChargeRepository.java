package com.daema.repository;

import com.daema.domain.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRepository extends JpaRepository<Charge, Number> , CustomChargeRepository {
}
