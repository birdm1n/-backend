package com.daema.repository;

import com.daema.domain.PubNotiRawData;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PubNotiRawDataRepository extends JpaRepository<PubNotiRawData, Number> {
    List<PubNotiRawData> findByDeadLineYn(String deadLineYn, Sort sort);
}
