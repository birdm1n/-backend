package com.daema.wms.repository;

import com.daema.wms.domain.Opening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpeningRepository extends JpaRepository<Opening, Long> , CustomOpeningRepository {

}
