package com.daema.rest.wms.service;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.enums.StatusEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.ProviderMgmtDto;
import com.daema.wms.domain.Provider;
import com.daema.wms.domain.dto.request.ProviderRequestDto;
import com.daema.wms.repository.ProviderRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProviderMgmtService {

	private final ProviderRepository providerRepository;

	private final AuthenticationUtil authenticationUtil;

	public ProviderMgmtService(ProviderRepository providerRepository, AuthenticationUtil authenticationUtil) {
		this.providerRepository = providerRepository;
		this.authenticationUtil = authenticationUtil;
	}

	public ResponseDto<ProviderMgmtDto> getProviderList(ProviderRequestDto requestDto) {

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
						.chargerPhone(providerMgmtDto.getChargerPhone())
						.returnZipCode(providerMgmtDto.getReturnZipCode())
						.returnAddr(providerMgmtDto.getReturnAddr())
						.returnAddrDetail(providerMgmtDto.getReturnAddrDetail())
						.useYn(StatusEnum.FLAG_Y.getStatusMsg())
						.regiUserId(authenticationUtil.getMemberSeq())
                        .regiDateTime(LocalDateTime.now())
						.updUserId(null)
						.updDateTime(null)
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
			provider.setChargerPhone(providerMgmtDto.getChargerPhone());
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

		if(delProvIds != null
			&& delProvIds.size() > 0){

			providerRepository.deleteAll(delProvIds.stream()
					.map(id -> new Provider(id.longValue()))
					.collect(Collectors.toList())
			);
		}else{
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
}



































