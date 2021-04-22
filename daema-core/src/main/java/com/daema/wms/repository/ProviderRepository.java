package com.daema.wms.repository;

import com.daema.wms.domain.Provider;
import com.daema.wms.repository.custom.CustomProviderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> , CustomProviderRepository {
}
