package com.daema.core.scm.repository.util;

import com.daema.core.scm.domain.join.ApplicationJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinInfoRepository extends JpaRepository<ApplicationJoin, Long> {
}
