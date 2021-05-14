package com.daema.rest.wms.service;

import com.daema.base.domain.Member;
import com.daema.base.enums.StatusEnum;
import com.daema.base.repository.MemberRepository;
import com.daema.commgmt.domain.Store;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.wms.dto.MoveStockAlarmDto;
import com.daema.rest.wms.dto.StoreStockMgmtDto;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.MoveStockAlarm;
import com.daema.wms.domain.StoreStock;
import com.daema.wms.domain.StoreStockCheck;
import com.daema.wms.domain.dto.request.StoreStockRequestDto;
import com.daema.wms.domain.dto.response.DeviceStatusListDto;
import com.daema.wms.domain.dto.response.StoreStockResponseDto;
import com.daema.wms.repository.MoveStockAlarmRepository;
import com.daema.wms.repository.StoreStockCheckRepository;
import com.daema.wms.repository.StoreStockRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreStockMgmtService {

	private final DeviceMgmtService deviceMgmtService;
	private final MoveStockAlarmRepository moveStockAlarmRepository;
	private final StoreStockRepository storeStockRepository;
	private final StoreStockCheckRepository storeStockCheckRepository;
	private final MemberRepository memberRepository;
	private final MoveStockMgmtService moveStockMgmtService;

	private final AuthenticationUtil authenticationUtil;

	public StoreStockMgmtService(DeviceMgmtService deviceMgmtService, MoveStockAlarmRepository moveStockAlarmRepository, StoreStockRepository storeStockRepository, StoreStockCheckRepository storeStockCheckRepository, MemberRepository memberRepository
			, MoveStockMgmtService moveStockMgmtService, AuthenticationUtil authenticationUtil) {
		this.deviceMgmtService = deviceMgmtService;
		this.moveStockAlarmRepository = moveStockAlarmRepository;
		this.storeStockRepository = storeStockRepository;
		this.storeStockCheckRepository = storeStockCheckRepository;
		this.memberRepository = memberRepository;
		this.moveStockMgmtService = moveStockMgmtService;
		this.authenticationUtil = authenticationUtil;
	}

	public ResponseDto<StoreStockResponseDto> getStoreStockList(StoreStockRequestDto requestDto) {

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
		//상점내 재고 여부 확인하고 없으면 인서트 or 업데이트
		StoreStock storeStock = storeStockRepository.findByStoreAndDevice(storeStockDto.getStore(), storeStockDto.getDevice());

		if(storeStock == null){
			return storeStockRepository.save(StoreStockMgmtDto.toEntity(storeStockDto));
		}else{
			return storeStock.updateStoreStock(storeStock, StoreStockMgmtDto.toEntity(storeStockDto));
		}
	}

	@Transactional
	public void checkStoreStock(String fullBarcode){

		Device device = deviceMgmtService.retrieveFullBarcode(fullBarcode);

		//상점내 재고 여부 확인하고 있으면 재고조사 or Exception
		StoreStock storeStock = storeStockRepository.findByStoreAndDeviceAndStockYn(
				Store.builder()
						.storeId(authenticationUtil.getStoreId())
						.build()
				, device
				, StatusEnum.FLAG_Y.getStatusMsg());

		if(storeStock != null){
			storeStockCheckRepository.save(
					StoreStockCheck.builder()
							.storeStockChkId(0L)
							.storeStock(storeStock)
							.regiUserId(Member.builder()
									.seq(authenticationUtil.getMemberSeq())
									.build())
							.regiDateTime(LocalDateTime.now())
					.build()
			);
		}else{
			throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
		}
	}

	public ResponseDto<StoreStockResponseDto> getLongTimeStoreStockList(StoreStockRequestDto requestDto) {

		requestDto.setStoreId(authenticationUtil.getStoreId());

		Page<StoreStockResponseDto> dataList = storeStockRepository.getLongTimeStoreStockList(requestDto);

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

	public MoveStockAlarmDto getLongTimeStoreStockAlarm(){

		Store store = Store.builder().storeId(authenticationUtil.getStoreId()).build();

		MoveStockAlarm moveStockAlarm = moveStockAlarmRepository.findByStore(store);

		return MoveStockAlarmDto.from(moveStockAlarm);
	}

	public void cuMoveStockAlarm(MoveStockAlarmDto requestDto){
		requestDto.setStoreId(authenticationUtil.getStoreId());
		requestDto.setMemberSeq(authenticationUtil.getMemberSeq());

		moveStockMgmtService.setLongTimeStoreStockAlarm(requestDto);
	}
}



































