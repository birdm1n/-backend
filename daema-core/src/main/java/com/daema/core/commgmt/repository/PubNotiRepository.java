package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.PubNoti;
import com.daema.core.commgmt.repository.custom.CustomPubNotiRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubNotiRepository extends JpaRepository<PubNoti, Number> , CustomPubNotiRepository {
    PubNoti findTopByGoodsIdOrderByRegiDateTimeDesc(long goodsId);
}
