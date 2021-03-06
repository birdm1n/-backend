package com.daema.rest.wms.service;

import com.daema.core.base.domain.Members;
import com.daema.core.base.enums.StatusEnum;
import com.daema.core.base.repository.MemberRepository;
import com.daema.core.commgmt.domain.Store;
import com.daema.rest.base.dto.response.ResponseDto;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.core.wms.domain.*;
import com.daema.core.wms.domain.enums.WmsEnum;
import com.daema.core.wms.dto.DeviceJudgeDto;
import com.daema.core.wms.dto.MoveStockAlarmDto;
import com.daema.core.wms.dto.StoreStockMgmtDto;
import com.daema.core.wms.dto.request.StoreStockRequestDto;
import com.daema.core.wms.dto.response.StoreStockCheckListDto;
import com.daema.core.wms.dto.response.StoreStockResponseDto;
import com.daema.core.wms.repository.DeviceJudgeRepository;
import com.daema.core.wms.repository.MoveStockAlarmRepository;
import com.daema.core.wms.repository.StoreStockCheckRepository;
import com.daema.core.wms.repository.StoreStockRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoreStockMgmtService {

	private final DeviceMgmtService deviceMgmtService;
	private final MoveStockAlarmRepository moveStockAlarmRepository;
	private final StoreStockRepository storeStockRepository;
	private final StoreStockCheckRepository storeStockCheckRepository;
	private final DeviceJudgeRepository deviceJudgeRepository;

	private final MoveStockMgmtService moveStockMgmtService;

	private final AuthenticationUtil authenticationUtil;

	public StoreStockMgmtService(DeviceMgmtService deviceMgmtService, MoveStockAlarmRepository moveStockAlarmRepository, StoreStockRepository storeStockRepository, StoreStockCheckRepository storeStockCheckRepository, MemberRepository memberRepository
			, DeviceJudgeRepository deviceJudgeRepository, MoveStockMgmtService moveStockMgmtService, AuthenticationUtil authenticationUtil) {
		this.deviceMgmtService = deviceMgmtService;
		this.moveStockAlarmRepository = moveStockAlarmRepository;
		this.storeStockRepository = storeStockRepository;
		this.storeStockCheckRepository = storeStockCheckRepository;
		this.deviceJudgeRepository = deviceJudgeRepository;
		this.moveStockMgmtService = moveStockMgmtService;
		this.authenticationUtil = authenticationUtil;
	}

	public ResponseDto<StoreStockResponseDto> getStoreStockList(StoreStockRequestDto requestDto, WmsEnum.StoreStockPathType pathType) {

		requestDto.setStoreId(authenticationUtil.getStoreId());

		Page<StoreStockResponseDto> dataList = null;

		if(WmsEnum.StoreStockPathType.STORE_STOCK == pathType){
			dataList = storeStockRepository.getStoreStockList(requestDto);
		}else if(WmsEnum.StoreStockPathType.LONG_TIME_STORE_STOCK == pathType){
			dataList = storeStockRepository.getLongTimeStoreStockList(requestDto);
		}else if(WmsEnum.StoreStockPathType.FAULTY_STORE_STOCK == pathType){
			dataList = storeStockRepository.getFaultyStoreStockList(requestDto);
		}

		return new ResponseDto(dataList);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public StoreStock cuStoreStock(StoreStockMgmtDto storeStockDto){
		//????????? ?????? ?????? ???????????? ????????? ????????? or ????????????
		StoreStock storeStock = storeStockRepository.findByStoreAndDevice(storeStockDto.getStore(), storeStockDto.getDevice());

		if(storeStock == null){
			return storeStockRepository.save(StoreStockMgmtDto.toEntity(storeStockDto));
		}else{
			return storeStock.updateStoreStock(storeStock, StoreStockMgmtDto.toEntity(storeStockDto));
		}
	}

	@Transactional
	public void checkStoreStock(String selDvcId){

		Device device = deviceMgmtService.retrieveDeviceFromSelDvcId(selDvcId);

		//????????? ?????? ?????? ???????????? ????????? ???????????? or Exception
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
							.regiUserId(Members.builder()
									.seq(authenticationUtil.getMemberSeq())
									.build())
							.regiDateTime(LocalDateTime.now())
							.build()
			);

			storeStock.updateStoreStockCheck(storeStock);
		}else{
			throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
		}
	}

	public List<StoreStockCheckListDto> getStoreStockCheckHistory(Long storeStockId) {

		List<StoreStockCheckListDto> storeStockCheckList = storeStockCheckRepository.getStoreStockCheckHistory(storeStockId);

		return storeStockCheckList;
	}

	public MoveStockAlarmDto getLongTimeStoreStockAlarm(){

		Store store = Store.builder().storeId(authenticationUtil.getStoreId()).build();

		MoveStockAlarm moveStockAlarm = moveStockAlarmRepository.findByStore(store);

		//??????????????? ?????? ???????????? ?????????, null ??? ?????? ???????????? ????????? ??? ??????
		//SaleStoreMgmtService.java > insertStoreAndUserAndStoreMap
		//moveStockMgmtService.setLongTimeStoreStockAlarm(moveStockAlarmDto);
		if(moveStockAlarm == null){
			moveStockAlarm = MoveStockAlarm.builder()
					.resellDay(30)
					.undeliveredDay(15)
					.store(Store.builder()
							.storeId(authenticationUtil.getStoreId())
							.build())
					.build();
		}

		return MoveStockAlarmDto.from(moveStockAlarm);
	}

	public void cuMoveStockAlarm(MoveStockAlarmDto requestDto){
		requestDto.setStoreId(authenticationUtil.getStoreId());
		requestDto.setMemberSeq(authenticationUtil.getMemberSeq());

		moveStockMgmtService.setLongTimeStoreStockAlarm(requestDto);
	}

	public void updateJudgementStatus(DeviceJudgeDto requestDto){

		Device device = Device.builder()
				.dvcId(requestDto.getDvcId())
				.build();

		List<DeviceJudge> deviceJudgeList = deviceJudgeRepository.findByDevice(device);

		if(CommonUtil.isNotEmptyList(deviceJudgeList)){
			deviceJudgeList.forEach(
					deviceJudge -> deviceJudge.updateDelYn(deviceJudge, StatusEnum.FLAG_Y.getStatusMsg())
			);
		}

		deviceJudgeRepository.save(
				DeviceJudge.builder()
						.dvcJudgeId(0L)
						.judgeStatus(requestDto.getJudgeStatus())
						.judgeMemo(requestDto.getJudgeMemo())
						.device(device)
						.build()
		);
	}
}



































