package com.daema.core.scm.repository.util;

import com.daema.core.scm.domain.join.ApplicationJoinAddService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinAddServiceRepository extends JpaRepository<ApplicationJoinAddService, Long> {
}
