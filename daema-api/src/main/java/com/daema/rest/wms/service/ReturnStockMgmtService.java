package com.daema.rest.wms.service;

import com.daema.base.domain.Member;
import com.daema.base.enums.StatusEnum;
import com.daema.base.enums.TypeEnum;
import com.daema.commgmt.domain.Store;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.io.file.FileUpload;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.wms.dto.StoreStockMgmtDto;
import com.daema.wms.domain.*;
import com.daema.wms.domain.dto.request.DeviceStatusDto;
import com.daema.wms.domain.dto.request.ReturnStockReqDto;
import com.daema.wms.domain.dto.request.ReturnStockRequestDto;
import com.daema.wms.domain.dto.response.ReturnStockResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
	private final FileUpload fileUpload;

	public ReturnStockMgmtService(StockRepository stockRepository, ReturnStockRepository returnStockRepository, DeviceRepository deviceRepository
			, DeviceStatusRepository deviceStatusRepository, MoveStockRepository deviceStockRepository
			, AuthenticationUtil authenticationUtil, ReturnStockCtrl returnStockCtrl, FileUpload fileUpload) {
		this.stockRepository = stockRepository;
		this.returnStockRepository = returnStockRepository;
		this.deviceRepository = deviceRepository;
		this.deviceStatusRepository = deviceStatusRepository;
		this.deviceStockRepository = deviceStockRepository;
		this.authenticationUtil = authenticationUtil;
		this.returnStockCtrl = returnStockCtrl;
		this.fileUpload = fileUpload;
	}

	public ResponseDto<ReturnStockResponseDto> getReturnStockList(ReturnStockRequestDto requestDto) {

		requestDto.setStoreId(authenticationUtil.getStoreId());

		Page<ReturnStockResponseDto> dataList = returnStockRepository.getSearchPage(requestDto);

		return new ResponseDto(dataList);
	}

	public Set<String> insertReturnStock(List<ReturnStockReqDto> returnStockDtoList) {

		Set<String> failBarcode = new HashSet<>();

		if (CommonUtil.isNotEmptyList(returnStockDtoList)) {

			//내 보유 창고에서 1건 추출
			Stock stock = stockRepository.findTopByRegiStoreIdAndStockTypeAndDelYn(authenticationUtil.getStoreId(),
					TypeEnum.STOCK_TYPE_I.getStatusCode(), StatusEnum.FLAG_N.getStatusMsg());

			if (stock != null) {
				for (ReturnStockReqDto returnStockDto : returnStockDtoList) {
					try {
						if(!returnStockCtrl.save(returnStockDto, stock)){
							failBarcode.add(returnStockDto.getFullBarcode());
						}
					}catch (Exception e){
						failBarcode.add(returnStockDto.getFullBarcode());
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

	public Set<String> insertReturnStockExcel(MultipartHttpServletRequest mRequest) {

		Set<String> failBarcode = new HashSet<>();

		try{
			Map<String, Object> excelMap = fileUpload.uploadExcelAndParser(mRequest.getFile("excelFile"), authenticationUtil.getMemberSeq());

			if(excelMap != null) {
				LinkedHashMap<String, String> headerMap = (LinkedHashMap<String, String>) excelMap.get("headers");
				List<HashMap<String, String>> barcodeList = (List<HashMap<String, String>>) excelMap.get("rows");

				if (CommonUtil.isNotEmptyList(barcodeList)) {
					String key = headerMap.keySet().iterator().next();

					//엑셀에서 추출한 바코드 리스트
					List<String> excelBarcodeList = barcodeList.stream()
							.map(data -> data.get(headerMap.get(key)))
							.collect(Collectors.toList());

					//바코드 기준으로 반품 할 데이터 조회 및 생성
					List<ReturnStockReqDto> returnStockDtoList = returnStockRepository.makeReturnStockInfoFromBarcode(excelBarcodeList, authenticationUtil.getStoreId());

					//반품 처리
					//failBarcode = insertReturnStock(returnStockDtoList);

					//DB 조회 된 바코드 리스트 추출
					Set<String> dbBarcode = returnStockDtoList.stream()
							.map(ReturnStockReqDto::getFullBarcode)
							.collect(Collectors.toSet());

					//엑셀 바코드 중 DB 에 없는 건은 failBarcode 에 add
					failBarcode.addAll(
							excelBarcodeList.stream()
									.filter(excelBarcode -> !dbBarcode.contains(excelBarcode))
							.collect(Collectors.toSet())
					);

				}else{
					throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
				}
			}
		}catch (Exception e){
			throw new ProcessErrorException(e.getMessage());
		}

		return failBarcode;
	}
}

@Service
class ReturnStockCtrl {

	private final ReturnStockRepository returnStockRepository;
	private final DeviceRepository deviceRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final StoreStockMgmtService storeStockMgmtService;
	private final StoreStockHistoryMgmtService storeStockHistoryMgmtService;
	private final AuthenticationUtil authenticationUtil;

	public ReturnStockCtrl(ReturnStockRepository returnStockRepository, DeviceRepository deviceRepository
			, DeviceStatusRepository deviceStatusRepository
			, StoreStockMgmtService storeStockMgmtService
			, StoreStockHistoryMgmtService storeStockHistoryMgmtService, AuthenticationUtil authenticationUtil) {
		this.returnStockRepository = returnStockRepository;
		this.deviceRepository = deviceRepository;
		this.deviceStatusRepository = deviceStatusRepository;
		this.storeStockMgmtService = storeStockMgmtService;
		this.storeStockHistoryMgmtService = storeStockHistoryMgmtService;
		this.authenticationUtil = authenticationUtil;
	}

	@Transactional
	public boolean save(ReturnStockReqDto returnStockDto, Stock stock) {

		boolean success = false;

		LocalDateTime regiDatetime = LocalDateTime.now();
		Device device = deviceRepository.findById(returnStockDto.getDvcId()).orElseGet(null);

		if (device != null) {

			List<ReturnStock> returnStocks = returnStockRepository.findByDeviceAndStore(
					device,
					Store.builder()
							.storeId(authenticationUtil.getStoreId())
							.build()
			);

			List<DeviceStatus> deviceStatuses = deviceStatusRepository.findByDevice(device);

			//기존 기기 상태 이력 업데이트
			if(CommonUtil.isNotEmptyList(deviceStatuses)){
				deviceStatuses.forEach(
						deviceStatus -> {
							deviceStatus.updateDelYn(deviceStatus, StatusEnum.FLAG_Y.getStatusMsg());
						}
				);
			}

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
							.inStockStatus(deviceStatusDto.getInStockStatus())
							.ddctReleaseAmtYn(deviceStatusDto.getDdctReleaseAmtYn())
							.device(device)
							.build()
			);

			//기존 반품 이력 업데이트
			if(CommonUtil.isNotEmptyList(returnStocks)){
				returnStocks.forEach(
						returnStock -> {
							returnStock.updateDelYn(returnStock, StatusEnum.FLAG_Y.getStatusMsg());
						}
				);
			}

			ReturnStock returnStock = returnStockRepository.save(
					ReturnStock.builder()
							.returnStockId(0L)
							.returnStockAmt(returnStockDto.getReturnStockAmt())
							.returnStockMemo(returnStockDto.getReturnStockMemo())
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
							.delYn(StatusEnum.FLAG_N.getStatusMsg())
							.build()
			);

			StoreStock storeStock = storeStockMgmtService.cuStoreStock(
					StoreStockMgmtDto.builder()
							.storeStockId(0L)
							.store(returnStock.getStore())
							.stockType(WmsEnum.StockType.RETURN_STOCK)
							.stockYn(StatusEnum.FLAG_Y.getStatusMsg())
							.stockTypeId(returnStock.getReturnStockId())
							.device(returnStock.getDevice())
							.prevStock(returnStock.getPrevStock())
							.nextStock(returnStock.getNextStock())
							.build()
			);

			storeStockHistoryMgmtService.insertStoreStockHistory(storeStock);
			storeStockHistoryMgmtService.arrangeStoreStockHistory(storeStock, false);

			success = true;

		}

		return success;
	}
}

















