package com.daema.rest.wms.service;

import com.daema.base.enums.StatusEnum;
import com.daema.base.repository.CodeDetailRepository;
import com.daema.commgmt.domain.Store;
import com.daema.rest.commgmt.dto.GoodsMgmtDto;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.DeviceDto;
import com.daema.rest.wms.dto.InStockMgmtDto;
import com.daema.rest.wms.dto.StockMgmtDto;
import com.daema.rest.wms.dto.response.DeviceResponseDto;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.Stock;
import com.daema.wms.repository.DeviceRepository;
import com.daema.wms.repository.DeviceStatusRepository;
import com.daema.wms.repository.InStockRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceMgmtService {

	private final InStockRepository inStockRepository;
	private final DeviceRepository deviceRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final CodeDetailRepository codeDetailRepository;
	private final AuthenticationUtil authenticationUtil;

	public DeviceMgmtService(InStockRepository inStockRepository, DeviceRepository deviceRepository,
							 DeviceStatusRepository deviceStatusRepository, CodeDetailRepository codeDetailRepository,
							 AuthenticationUtil authenticationUtil) {
		this.inStockRepository = inStockRepository;
		this.deviceRepository = deviceRepository;
		this.deviceStatusRepository = deviceStatusRepository;
		this.codeDetailRepository = codeDetailRepository;
		this.authenticationUtil = authenticationUtil;
	}

	public DeviceResponseDto getDeviceInfoFromFullBarcode(String fullBarcode){

		Device device = retrieveFullBarcode(fullBarcode);

		DeviceResponseDto deviceResponseDto = new DeviceResponseDto();

		if(device != null) {
			device.getGoodsOption().getGoods().setMakerName(
					codeDetailRepository.findById(device.getGoodsOption().getGoods().getMaker()).get().getCodeNm()
			);

			device.getGoodsOption().getGoods().setNetworkName(
					codeDetailRepository.findById(device.getGoodsOption().getGoods().getNetworkAttribute().network).get().getCodeNm()
			);

			device.getGoodsOption().getGoods().setTelecomName(
					codeDetailRepository.findById(device.getGoodsOption().getGoods().getNetworkAttribute().telecom).get().getCodeNm()
			);

			deviceResponseDto.setDeviceDto(DeviceDto.from(device));

			device.getInStocks().get(0).setRegiUserId(null);
			device.getInStocks().get(0).setUpdUserId(null);
			deviceResponseDto.setInStockMgmtDto(InStockMgmtDto.from(device.getInStocks().get(0)));

			device.getGoodsOption().getGoods().setOptionList(null);
			deviceResponseDto.setGoodsMgmtDto(GoodsMgmtDto.from(device.getGoodsOption().getGoods()));

			Stock stock = device.getStoreStock().getNextStock() != null ? device.getStoreStock().getNextStock() : device.getStoreStock().getPrevStock();

			deviceResponseDto.setStockMgmtDto(StockMgmtDto.from(stock));
		}else{
			deviceResponseDto = null;
		}

		return deviceResponseDto;
	}

	public Device retrieveFullBarcode(String fullBarcode){
		return deviceRepository.findByFullBarcodeAndStoreAndDelYn(fullBarcode
				, Store.builder().storeId(authenticationUtil.getStoreId()).build()
				, StatusEnum.FLAG_N.getStatusMsg());
	}
}