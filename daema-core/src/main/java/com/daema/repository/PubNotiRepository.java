package com.daema.repository;

import com.daema.domain.PubNoti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubNotiRepository extends JpaRepository<PubNoti, Number> , CustomPubNotiRepository {
}
