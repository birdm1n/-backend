package com.daema.core.sms.repository;

import com.daema.core.sms.domain.JoinInfo;
import com.daema.core.sms.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinInfoRepository extends JpaRepository<JoinInfo, Long> {
}
