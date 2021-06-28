package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.AddServiceRegReq;
import com.daema.core.commgmt.repository.custom.CustomAddServiceRegReqRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddServiceRegReqRepository extends JpaRepository<AddServiceRegReq, Long>, CustomAddServiceRegReqRepository {
}
