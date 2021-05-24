package com.daema.wms.repository;

import com.daema.wms.domain.Provider;
import com.daema.wms.repository.custom.CustomProviderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderRepository extends JpaRepository<Provider, Long> , CustomProviderRepository {
    List<Provider> findByStoreIdAndUseYnAndDelYnOrderByProvName(long storeId, String useYn, String delYn);

    Provider findByProvIdAndDelYn(Long provId, String statusMsg);
}
