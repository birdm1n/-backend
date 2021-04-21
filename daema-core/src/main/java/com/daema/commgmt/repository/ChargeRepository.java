package com.daema.commgmt.repository;

import com.daema.commgmt.domain.Charge;
import com.daema.commgmt.repository.custom.CustomChargeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRepository extends JpaRepository<Charge, Number> , CustomChargeRepository {
}
