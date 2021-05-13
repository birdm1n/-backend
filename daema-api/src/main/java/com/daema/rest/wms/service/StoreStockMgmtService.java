package com.daema.rest.wms.service;

import com.daema.base.repository.MemberRepository;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.wms.dto.StoreStockMgmtDto;
import com.daema.rest.wms.dto.response.StockMgmtResponseDto;
import com.daema.wms.domain.StoreStock;
import com.daema.wms.domain.dto.request.StoreStockRequestDto;
import com.daema.wms.domain.dto.response.DeviceStatusListDto;
import com.daema.wms.domain.dto.response.StoreStockResponseDto;
import com.daema.wms.repository.StoreStockRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreStockMgmtService {

	private final DeviceMgmtService deviceMgmtService;

	private final StoreStockRepository storeStockRepository;
	private final MemberRepository memberRepository;

	private final AuthenticationUtil authenticationUtil;

	public StoreStockMgmtService(DeviceMgmtService deviceMgmtService, StoreStockRepository storeStockRepository, MemberRepository memberRepository
			, AuthenticationUtil authenticationUtil) {
		this.deviceMgmtService = deviceMgmtService;
		this.storeStockRepository = storeStockRepository;
		this.memberRepository = memberRepository;
		this.authenticationUtil = authenticationUtil;
	}

	public ResponseDto<StockMgmtResponseDto> getStoreStockList(StoreStockRequestDto requestDto) {

		requestDto.setStoreId(authenticationUtil.getStoreId());

		Page<StoreStockResponseDto> dataList = storeStockRepository.getStoreStockList(requestDto);

		//기기 최종 상태 가져오기
		List<Long> dvcIds = Optional.ofNullable(dataList.getContent())
				.orElseGet(Collections::emptyList)
				.stream()
				.map(StoreStockResponseDto::getDvcId)
				.collect(Collectors.toList());

		List<DeviceStatusListDto> deviceStatusListDtoList = deviceMgmtService.getLastDeviceStatusInfo(dvcIds);

		if(CommonUtil.isNotEmptyList(deviceStatusListDtoList)){
			dataList.getContent()
					.forEach(
							stock -> {
								stock.setDeviceStatusListDto(deviceStatusListDtoList.stream()
										.filter(device -> stock.getDvcId().equals(device.getDvcId()))
								.findAny().orElse(null));
							}
					);
		}

		return new ResponseDto(dataList);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public StoreStock cuStoreStock(StoreStockMgmtDto storeStockDto){

		StoreStock storeStock = storeStockRepository.findByStoreAndDevice(storeStockDto.getStore(), storeStockDto.getDevice());

		if(storeStock == null){
			return storeStockRepository.save(StoreStockMgmtDto.toEntity(storeStockDto));
		}else{
			return storeStock.updateStoreStock(storeStock, StoreStockMgmtDto.toEntity(storeStockDto));
		}
	}
}



































