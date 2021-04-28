package com.daema.commgmt.repository;

import com.daema.commgmt.domain.PubNotiRawData;
import com.daema.commgmt.repository.custom.CustomPubNotiRawDataRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubNotiRawDataRepository extends JpaRepository<PubNotiRawData, Number>, CustomPubNotiRawDataRepository {
    boolean existsByDeadLineYn(String deadLineYn);
}
