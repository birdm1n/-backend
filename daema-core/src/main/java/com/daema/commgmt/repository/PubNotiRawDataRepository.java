package com.daema.commgmt.repository;

import com.daema.commgmt.domain.PubNotiRawData;
import com.daema.commgmt.repository.custom.CustomPubNotiRawDataRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PubNotiRawDataRepository extends JpaRepository<PubNotiRawData, Number>, CustomPubNotiRawDataRepository {
    List<PubNotiRawData> findByDeadLineYn(String deadLineYn, Sort sort);
    boolean existsByDeadLineYn(String deadLineYn);
}
