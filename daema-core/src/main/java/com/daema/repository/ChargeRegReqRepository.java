package com.daema.repository;

import com.daema.domain.ChargeRegReq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRegReqRepository extends JpaRepository<ChargeRegReq, Long>, CustomChargeRegReqRepository {
}
