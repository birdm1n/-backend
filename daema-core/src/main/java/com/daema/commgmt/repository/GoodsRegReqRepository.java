package com.daema.commgmt.repository;

import com.daema.commgmt.domain.GoodsRegReq;
import com.daema.commgmt.repository.custom.CustomGoodsRegReqRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRegReqRepository extends JpaRepository<GoodsRegReq, Long>, CustomGoodsRegReqRepository {
}
