package com.daema.core.sms.repository;

import com.daema.core.sms.domain.JoinAddition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinInfoAdditionRepository extends JpaRepository<JoinAddition, Long> {
}
