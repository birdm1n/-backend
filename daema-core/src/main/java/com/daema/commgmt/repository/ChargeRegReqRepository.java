package com.daema.commgmt.repository;

import com.daema.commgmt.domain.ChargeRegReq;
import com.daema.commgmt.repository.custom.CustomChargeRegReqRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRegReqRepository extends JpaRepository<ChargeRegReq, Long>, CustomChargeRegReqRepository {
}
