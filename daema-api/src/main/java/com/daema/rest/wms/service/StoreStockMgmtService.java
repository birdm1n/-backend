package com.daema.rest.wms.service;

import com.daema.base.repository.MemberRepository;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.StoreStockMgmtDto;
import com.daema.rest.wms.dto.response.StockMgmtResponseDto;
import com.daema.wms.domain.StoreStock;
import com.daema.wms.domain.dto.request.StoreStockRequestDto;
import com.daema.wms.domain.dto.response.StoreStockResponseDto;
import com.daema.wms.repository.StoreStockRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class StoreStockMgmtService {

	private final StoreStockRepository storeStockRepository;

	private final MemberRepository memberRepository;

	private final AuthenticationUtil authenticationUtil;

	public StoreStockMgmtService(StoreStockRepository storeStockRepository, MemberRepository memberRepository
			, AuthenticationUtil authenticationUtil) {
		this.storeStockRepository = storeStockRepository;
		this.memberRepository = memberRepository;
		this.authenticationUtil = authenticationUtil;
	}

	public StockMgmtResponseDto getStoreStockList(StoreStockRequestDto requestDto) {

		requestDto.setStoreId(authenticationUtil.getStoreId());

		StockMgmtResponseDto stockMgmtResponseDto = new StockMgmtResponseDto();
		
		Page<StoreStockResponseDto> dataList = storeStockRepository.getStoreStockList(requestDto);

		return stockMgmtResponseDto;
	}

	public StoreStock cuStoreStock(StoreStockMgmtDto storeStockDto){

		StoreStock storeStock = storeStockRepository.findByStoreAndDevice(storeStockDto.getStore(), storeStockDto.getDevice());

		if(storeStock == null){
			return storeStockRepository.save(StoreStockMgmtDto.toEntity(storeStockDto));
		}else{
			return storeStock.updateStoreStock(storeStock, StoreStockMgmtDto.toEntity(storeStockDto));
		}
	}
}



































