package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.Charge;
import com.daema.core.commgmt.repository.custom.CustomChargeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRepository extends JpaRepository<Charge, Number> , CustomChargeRepository {
}
