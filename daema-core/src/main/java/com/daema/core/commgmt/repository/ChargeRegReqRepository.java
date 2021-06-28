package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.ChargeRegReq;
import com.daema.core.commgmt.repository.custom.CustomChargeRegReqRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRegReqRepository extends JpaRepository<ChargeRegReq, Long>, CustomChargeRegReqRepository {
}
