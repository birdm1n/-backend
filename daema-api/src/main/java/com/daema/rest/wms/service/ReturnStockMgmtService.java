package com.daema.rest.wms.service;

import com.daema.core.base.domain.Members;
import com.daema.core.base.enums.StatusEnum;
import com.daema.core.base.enums.TypeEnum;
import com.daema.core.commgmt.domain.Store;
import com.daema.rest.base.dto.response.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.io.file.FileUpload;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.core.wms.domain.*;
import com.daema.core.wms.domain.enums.WmsEnum;
import com.daema.core.wms.dto.DeviceStatusDto;
import com.daema.core.wms.dto.StoreStockMgmtDto;
import com.daema.core.wms.dto.request.ReturnStockReqDto;
import com.daema.core.wms.dto.request.ReturnStockRequestDto;
import com.daema.core.wms.dto.response.ReturnStockResDto;
import com.daema.core.wms.dto.response.ReturnStockResponseDto;
import com.daema.core.wms.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReturnStockMgmtService {

	private final StockRepository stockRepository;
	private final ReturnStockRepository returnStockRepository;
	private final DeviceRepository deviceRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final MoveStockRepository moveStockRepository;
	private final MoveStockMgmtService moveStockMgmtService;
	private final AuthenticationUtil authenticationUtil;
	private final ReturnStockCtrl returnStockCtrl;
	private final FileUpload fileUpload;



	public ResponseDto<ReturnStockResponseDto> getReturnStockList(ReturnStockRequestDto requestDto) {

		requestDto.setStoreId(authenticationUtil.getStoreId());

		Page<ReturnStockResponseDto> dataList = returnStockRepository.getSearchPage(requestDto);

		return new ResponseDto(dataList);
	}

	public List<String> insertReturnStock(List<ReturnStockReqDto> returnStockDtoList) {

		List<String> failBarcode = new ArrayList<>();

		if (CommonUtil.isNotEmptyList(returnStockDtoList)) {

			//??? ?????? ???????????? 1??? ??????
			Stock stock = stockRepository.findTopByRegiStoreIdAndStockTypeAndDelYn(authenticationUtil.getStoreId(),
					TypeEnum.STOCK_TYPE_I.getStatusCode(), StatusEnum.FLAG_N.getStatusMsg());

			if (stock != null) {
				for (ReturnStockReqDto returnStockDto : returnStockDtoList) {
					try {
						if(!returnStockCtrl.save(returnStockDto, stock)){
							failBarcode.add(returnStockDto.getRawBarcode());
						}
					}catch (Exception e){
						failBarcode.add(returnStockDto.getRawBarcode());
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

	public List<String> insertReturnStockExcel(MultipartHttpServletRequest mRequest) {

		List<String> failBarcode = new ArrayList<>();

		try{
			Map<String, Object> excelMap = fileUpload.uploadExcelAndParser(mRequest.getFile("excelFile"), authenticationUtil.getMemberSeq());

			if(excelMap != null) {
				LinkedHashMap<String, String> headerMap = (LinkedHashMap<String, String>) excelMap.get("headers");
				List<HashMap<String, String>> barcodeList = (List<HashMap<String, String>>) excelMap.get("rows");

				if (CommonUtil.isNotEmptyList(barcodeList)) {
					String key = headerMap.keySet().iterator().next();

					//???????????? ????????? ????????? ?????????
					List<String> excelBarcodeList = barcodeList.stream()
							.map(data -> data.get(headerMap.get(key)))
							.collect(Collectors.toList());

					//????????? ???????????? ?????? ??? ????????? ?????? ??? ??????
					List<ReturnStockResDto> returnStockDtoList = returnStockRepository.makeReturnStockInfoFromBarcode(excelBarcodeList, authenticationUtil.getStoreId());

					//api request ??? db response ??? ???????????? ???????????? ?????? res to req ?????? ??????
					List<ReturnStockReqDto> convertDataList = new ArrayList<>();
					ObjectMapper mapper = new ObjectMapper();

					if (CommonUtil.isNotEmptyList(returnStockDtoList)) {
						returnStockDtoList.forEach(
								data -> convertDataList.add(
										mapper.convertValue(data, ReturnStockReqDto.class)
								)
						);
					}

					//?????? ??????
					failBarcode = insertReturnStock(convertDataList);

					//DB ?????? ??? ????????? ????????? ??????
					Set<String> dbBarcode = returnStockDtoList.stream()
							.map(ReturnStockResDto::getRawBarcode)
							.collect(Collectors.toSet());

					//?????? ????????? ??? DB ??? ?????? ?????? failBarcode ??? add
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
@RequiredArgsConstructor
@Service
class ReturnStockCtrl {

	private final ReturnStockRepository returnStockRepository;
	private final DeviceRepository deviceRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final StoreStockMgmtService storeStockMgmtService;
	private final StoreStockHistoryMgmtService storeStockHistoryMgmtService;
	private final AuthenticationUtil authenticationUtil;
	private final MoveStockMgmtService moveStockMgmtService;

	@Transactional
	public boolean save(ReturnStockReqDto returnStockDto, Stock stock) {

		boolean success = false;

		LocalDateTime regiDatetime = LocalDateTime.now();
		Device device = deviceRepository.findById(returnStockDto.getDvcId()).orElseGet(null);

		if (device != null) {

			/* [??????] ?????? ??? ????????? ?????? */
			StoreStock prevStoreStock = device.getStoreStock();
			ResponseCodeEnum validCode = moveStockMgmtService.changeCkStockType(prevStoreStock, WmsEnum.StockType.RETURN_STOCK);
			if (validCode != ResponseCodeEnum.OK) {
				return success;
			}

			List<ReturnStock> returnStocks = returnStockRepository.findByDeviceAndStore(
					device,
					Store.builder()
							.storeId(authenticationUtil.getStoreId())
							.build()
			);

			List<DeviceStatus> deviceStatuses = deviceStatusRepository.findByDevice(device);

			//?????? ?????? ?????? ?????? ????????????
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

			//?????? ?????? ?????? ????????????
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
							.regiUserId(Members.builder()
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

















