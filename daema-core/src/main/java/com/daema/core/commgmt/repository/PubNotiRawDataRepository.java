package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.PubNotiRawData;
import com.daema.core.commgmt.repository.custom.CustomPubNotiRawDataRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubNotiRawDataRepository extends JpaRepository<PubNotiRawData, Number>, CustomPubNotiRawDataRepository {
    boolean existsByDeadLineYn(String deadLineYn);
}
