package com.daema.core.scm.repository.util;

import com.daema.core.scm.domain.joininfo.JoinAddService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinInfoAddServiceRepository extends JpaRepository<JoinAddService, Long> {
}
