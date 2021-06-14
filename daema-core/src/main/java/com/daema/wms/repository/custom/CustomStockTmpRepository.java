package com.daema.wms.repository.custom;

import com.daema.wms.domain.StockTmp;

import java.util.List;

public interface CustomStockTmpRepository {

    List<StockTmp> getTelkitStockList(Long[] moveType);
}
