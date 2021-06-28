package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.GoodsRegReq;
import com.daema.core.commgmt.repository.custom.CustomGoodsRegReqRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRegReqRepository extends JpaRepository<GoodsRegReq, Long>, CustomGoodsRegReqRepository {
}
