package com.daema.repository;

import com.daema.domain.AddServiceRegReq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddServiceRegReqRepository extends JpaRepository<AddServiceRegReq, Long>, CustomAddServiceRegReqRepository {
}
