package com.daema.repository;

import com.daema.domain.GoodsRegReq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRegReqRepository extends JpaRepository<GoodsRegReq, Long>, CustomGoodsRegReqRepository {
}
