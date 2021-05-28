package com.daema.rest.wms.service;

import com.daema.base.enums.StatusEnum;
import com.daema.commgmt.domain.Store;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.wms.dto.ProviderMgmtDto;
import com.daema.rest.wms.dto.response.SearchMatchResponseDto;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.InStock;
import com.daema.wms.domain.Provider;
import com.daema.wms.domain.dto.request.ProviderRequestDto;
import com.daema.wms.repository.DeviceRepository;
import com.daema.wms.repository.InStockRepository;
import com.daema.wms.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProviderMgmtService {

	private final ProviderRepository providerRepository;
	private final DeviceRepository deviceRepository;
	private final InStockRepository inStockRepository;

	private final AuthenticationUtil authenticationUtil;

	public ResponseDto<ProviderMgmtDto> getProviderList(ProviderRequestDto requestDto) {

		requestDto.setStoreId(authenticationUtil.getStoreId());
		
		Page<Provider> dataList = providerRepository.getSearchPage(requestDto);

		return new ResponseDto(ProviderMgmtDto.class, dataList);
	}

	@Transactional
	public void insertProvider(ProviderMgmtDto providerMgmtDto) {
		providerRepository.save(
				Provider.builder()
						.provId(providerMgmtDto.getProvId())
						.provName(providerMgmtDto.getProvName())
						.chargerName(providerMgmtDto.getChargerName())
						.chargerEmail(providerMgmtDto.getChargerEmail())
						.chargerPhone(
								providerMgmtDto.getChargerPhone1()
								.concat(providerMgmtDto.getChargerPhone2())
								.concat(providerMgmtDto.getChargerPhone3())
						)
						.chargerPhone1(providerMgmtDto.getChargerPhone1())
						.chargerPhone2(providerMgmtDto.getChargerPhone2())
						.chargerPhone3(providerMgmtDto.getChargerPhone3())
						.returnZipCode(providerMgmtDto.getReturnZipCode())
						.returnAddr(providerMgmtDto.getReturnAddr())
						.returnAddrDetail(providerMgmtDto.getReturnAddrDetail())
						.useYn(StatusEnum.FLAG_Y.getStatusMsg())
						.regiUserId(authenticationUtil.getMemberSeq())
                        .regiDateTime(LocalDateTime.now())
						.delYn(StatusEnum.FLAG_N.getStatusMsg())
						.updUserId(authenticationUtil.getMemberSeq())
						.updDateTime(LocalDateTime.now())
						.storeId(authenticationUtil.getStoreId())
                    .build()
        );
	}

	@Transactional
	public void updateProviderInfo(ProviderMgmtDto providerMgmtDto) {

		Provider provider = providerRepository.findById(providerMgmtDto.getProvId()).orElse(null);

		if(provider != null) {
			//TODO ifnull return 함수 추가
			provider.setProvId(providerMgmtDto.getProvId());
			provider.setProvName(providerMgmtDto.getProvName());
			provider.setChargerName(providerMgmtDto.getChargerName());
			provider.setChargerEmail(providerMgmtDto.getChargerEmail());
			provider.setChargerPhone(
					providerMgmtDto.getChargerPhone1()
					.concat(providerMgmtDto.getChargerPhone2())
					.concat(providerMgmtDto.getChargerPhone3())
			);
			provider.setChargerPhone1(providerMgmtDto.getChargerPhone1());
			provider.setChargerPhone2(providerMgmtDto.getChargerPhone2());
			provider.setChargerPhone3(providerMgmtDto.getChargerPhone3());
			provider.setReturnZipCode(providerMgmtDto.getReturnZipCode());
			provider.setReturnAddr(providerMgmtDto.getReturnAddr());
			provider.setReturnAddrDetail(providerMgmtDto.getReturnAddrDetail());
			provider.setUpdUserId(authenticationUtil.getMemberSeq());
			provider.setUpdDateTime(LocalDateTime.now());
		}else{
			throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
		}
	}

	@Transactional
	public void deleteProvider(ModelMap reqModelMap) {

		List<Number> delProvIds = (ArrayList<Number>) reqModelMap.get("delProvId");

		if (CommonUtil.isNotEmptyList(delProvIds)) {

			List<Provider> providerList = providerRepository.findAllById(
					delProvIds.stream()
							.map(Number::longValue).collect(Collectors.toList())
			);

			if(CommonUtil.isNotEmptyList(providerList)) {
				Optional.ofNullable(providerList).orElseGet(Collections::emptyList).forEach(provider -> {
					provider.updateDelYn(provider, StatusEnum.FLAG_Y.getStatusMsg(), authenticationUtil.getMemberSeq());
				});
			}else{
				throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
			}
		} else {
			throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
		}
	}

	@Transactional
	public void updateProviderUse(ModelMap reqModelMap) {

		long provId = Long.parseLong(String.valueOf(reqModelMap.getAttribute("provId")));
		String useYn = String.valueOf(reqModelMap.getAttribute("useYn"));

		Provider provider = providerRepository.findById(provId).orElse(null);

		if(provider != null) {
			provider.updateUseYn(provider, useYn, authenticationUtil.getMemberSeq());
		}else{
			throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
		}
	}

	public SearchMatchResponseDto getDeviceProvInfo(String fullBarcode) {
		long storeId = authenticationUtil.getStoreId();
		Store store = Store
				.builder()
				.storeId(storeId)
				.build();

		Device device = deviceRepository.findByFullBarcodeAndStoreAndDelYn(fullBarcode, store, "N");
		if(device == null){
			return null;
		}

		// [공급처] 정보 조회
		InStock inStock = inStockRepository.findByStoreAndDeviceAndDelYn(store, device,"N");

		Provider provider = inStock.getProvider();
		return SearchMatchResponseDto
				.builder()
				.provId(provider.getProvId())
				.provName(provider.getProvName())
				.build();
	}
}



































