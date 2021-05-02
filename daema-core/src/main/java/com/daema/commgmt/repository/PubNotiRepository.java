package com.daema.commgmt.repository;

import com.daema.commgmt.domain.PubNoti;
import com.daema.commgmt.repository.custom.CustomPubNotiRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubNotiRepository extends JpaRepository<PubNoti, Number> , CustomPubNotiRepository {
    PubNoti findTopByGoodsIdOrderByRegiDateTimeDesc(long goodsId);
}
