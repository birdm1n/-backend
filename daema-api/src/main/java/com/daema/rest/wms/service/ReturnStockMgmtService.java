package com.daema.rest.wms.service;

import com.daema.base.domain.Member;
import com.daema.base.enums.StatusEnum;
import com.daema.base.enums.TypeEnum;
import com.daema.commgmt.domain.Store;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.wms.dto.DeviceStatusDto;
import com.daema.rest.wms.dto.ReturnStockDto;
import com.daema.rest.wms.dto.request.ReturnStockReqDto;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.DeviceStatus;
import com.daema.wms.domain.ReturnStock;
import com.daema.wms.domain.Stock;
import com.daema.wms.domain.dto.request.ReturnStockRequestDto;
import com.daema.wms.domain.dto.response.ReturnStockResponseDto;
import com.daema.wms.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ReturnStockMgmtService {

	private final StockRepository stockRepository;
	private final ReturnStockRepository returnStockRepository;
	private final DeviceRepository deviceRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final MoveStockRepository deviceStockRepository;
	private final AuthenticationUtil authenticationUtil;
	private final ReturnStockCtrl returnStockCtrl;

	public ReturnStockMgmtService(StockRepository stockRepository, ReturnStockRepository returnStockRepository, DeviceRepository deviceRepository
			,DeviceStatusRepository deviceStatusRepository, MoveStockRepository deviceStockRepository
			,AuthenticationUtil authenticationUtil, ReturnStockCtrl returnStockCtrl) {
		this.stockRepository = stockRepository;
		this.returnStockRepository = returnStockRepository;
		this.deviceRepository = deviceRepository;
		this.deviceStatusRepository = deviceStatusRepository;
		this.deviceStockRepository = deviceStockRepository;
		this.authenticationUtil = authenticationUtil;
		this.returnStockCtrl = returnStockCtrl;
	}

	public ResponseDto<ReturnStockDto> getReturnStockList(ReturnStockRequestDto requestDto) {

		requestDto.setStoreId(authenticationUtil.getStoreId());

		Page<ReturnStockResponseDto> dataList = returnStockRepository.getSearchPage(requestDto);

		return new ResponseDto(dataList);
	}

	public Set<Long> insertReturnStock(List<ReturnStockReqDto> returnStockDtoList) {

		Set<Long> failBarcode = new HashSet<>();

		if (CommonUtil.isNotEmptyList(returnStockDtoList)) {

			//내 보유 창고에서 1건 추출
			Stock stock = stockRepository.findTopByRegiStoreIdAndStockTypeAndDelYn(authenticationUtil.getStoreId(),
					TypeEnum.STOCK_TYPE_I.getStatusCode(), StatusEnum.FLAG_N.getStatusMsg());

			if (stock != null) {
				for (ReturnStockReqDto returnStockDto : returnStockDtoList) {
					try {
						if(!returnStockCtrl.save(returnStockDto, stock)){
							failBarcode.add(returnStockDto.getDvcId());
						}
					}catch (Exception e){
						failBarcode.add(returnStockDto.getDvcId());
						log.error(e.getMessage());
					}
				}
			} else {
				throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
			}
		} else {
			throw new DataNotFoundException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
		}

		return failBarcode;
	}
}

@Service
class ReturnStockCtrl {

	private final ReturnStockRepository returnStockRepository;
	private final DeviceRepository deviceRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final MoveStockRepository deviceStockRepository;
	private final AuthenticationUtil authenticationUtil;

	public ReturnStockCtrl(ReturnStockRepository returnStockRepository, DeviceRepository deviceRepository
			, DeviceStatusRepository deviceStatusRepository, MoveStockRepository deviceStockRepository
			, AuthenticationUtil authenticationUtil) {
		this.returnStockRepository = returnStockRepository;
		this.deviceRepository = deviceRepository;
		this.deviceStatusRepository = deviceStatusRepository;
		this.deviceStockRepository = deviceStockRepository;
		this.authenticationUtil = authenticationUtil;
	}

	@Transactional
	public boolean save(ReturnStockReqDto returnStockDto, Stock stock) {

		boolean success = false;

		LocalDateTime regiDatetime = LocalDateTime.now();
		Device device = deviceRepository.findById(returnStockDto.getDvcId()).orElseGet(null);

		if (device != null) {
			DeviceStatusDto deviceStatusDto = returnStockDto.getReturnDeviceStatus();

			DeviceStatus deviceStatus = deviceStatusRepository.save(
					DeviceStatus.builder()
							.dvcStatusId(deviceStatusDto.getDvcStatusId())
							.productFaultyYn(deviceStatusDto.getProductFaultyYn())
							.extrrStatus(deviceStatusDto.getExtrrStatus())
							.productMissYn(deviceStatusDto.getProductMissYn())
							.missProduct(deviceStatusDto.getMissProduct())
							.ddctAmt(deviceStatusDto.getDdctAmt())
							.addDdctAmt(deviceStatusDto.getAddDdctAmt())
							.device(device)
							.build()
			);

			returnStockRepository.save(
					ReturnStock.builder()
							.returnStockId(0L)
							.returnStockStatus(returnStockDto.getReturnStockStatus())
							.returnStockAmt(returnStockDto.getReturnStockAmt())
							.returnStockMemo(returnStockDto.getReturnStockMemo())
							.ddctReleaseAmtYn(returnStockDto.getReturnDeviceStatus().getDdctReleaseAmtYn())
							.device(device)
							.prevStock(Stock.builder()
									.stockId(returnStockDto.getPrevStockId())
									.build())
							.nextStock(stock)
							.returnDeviceStatus(deviceStatus)
							.store(Store.builder()
									.storeId(authenticationUtil.getStoreId())
									.build())
							.regiUserId(Member.builder()
									.seq(authenticationUtil.getMemberSeq())
									.build())
							.regiDateTime(regiDatetime)
							.build()
			);

			success = true;

		}

		return success;
	}
}

















