package com.daema.core.wms.repository;

import com.daema.core.wms.domain.Provider;
import com.daema.core.wms.repository.custom.CustomProviderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderRepository extends JpaRepository<Provider, Long> , CustomProviderRepository {
    List<Provider> findByStoreIdAndUseYnAndDelYnOrderByProvName(long storeId, String useYn, String delYn);
    List<Provider> findByStoreIdAndDelYnOrderByProvName(long storeId, String delYn);

    Provider findByProvIdAndDelYn(Long provId, String statusMsg);
}
