package com.daema.rest.wms.service;

import com.daema.core.wms.domain.*;
import com.daema.core.wms.domain.enums.WmsEnum;
import com.daema.core.wms.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreStockHistoryMgmtService {

	private final StoreStockHistoryRepository storeStockHistoryRepository;

	public StoreStockHistoryMgmtService(StoreStockHistoryRepository storeStockHistoryRepository) {
		this.storeStockHistoryRepository = storeStockHistoryRepository;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void arrangeStoreStockHistory(StoreStock storeStock, boolean delFlag){
		storeStockHistoryRepository.arrangeStoreStockHistory(storeStock, delFlag);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertStoreStockHistory(StoreStock storeStock){
		storeStock.setHistoryStatus(WmsEnum.HistoryStatus.USE);
		storeStockHistoryRepository.save(storeStock.toHistoryEntity(storeStock));
	}
}



































