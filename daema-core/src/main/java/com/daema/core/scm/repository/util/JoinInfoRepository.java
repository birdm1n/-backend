package com.daema.core.scm.repository.util;

import com.daema.core.scm.domain.joininfo.JoinInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinInfoRepository extends JpaRepository<JoinInfo, Long> {
}
