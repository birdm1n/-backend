package com.daema.wms.repository;

import com.daema.wms.domain.InStockWait;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InStockWaitRepository extends JpaRepository<InStockWait, String> {

    InStockWait findByFullBarcodeAndDelYn(String fullBarcode, String statusMsg);
}
