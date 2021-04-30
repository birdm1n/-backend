package com.daema.wms.repository;

import com.daema.wms.domain.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarcodeRepository extends JpaRepository<Barcode, String> {

}
