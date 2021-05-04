package com.daema.wms.repository;

import com.daema.wms.domain.InStockWait;
import com.daema.wms.repository.custom.CustomInStockWaitRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InStockWaitRepository extends JpaRepository<InStockWait, Long>, CustomInStockWaitRepository {

    InStockWait findByFullBarcodeAndDelYn(String fullBarcode, String statusMsg);

}
