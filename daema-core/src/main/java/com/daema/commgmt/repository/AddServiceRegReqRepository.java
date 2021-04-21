package com.daema.commgmt.repository;

import com.daema.commgmt.domain.AddServiceRegReq;
import com.daema.commgmt.repository.custom.CustomAddServiceRegReqRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddServiceRegReqRepository extends JpaRepository<AddServiceRegReq, Long>, CustomAddServiceRegReqRepository {
}
