package com.daema.core.wms.repository.custom;

import com.daema.core.wms.domain.StockTmp;

import java.util.List;

public interface CustomStockTmpRepository {

    List<StockTmp> getTelkitStockList(Long[] moveType);
}
