package com.daema.service;

import com.daema.common.Constants;
import com.daema.common.enums.StatusEnum;
import com.daema.common.util.AuthenticationUtil;
import com.daema.domain.Store;
import com.daema.domain.StoreMap;
import com.daema.domain.pk.StoreMapPK;
import com.daema.dto.*;
import com.daema.dto.common.ResponseDto;
import com.daema.repository.StoreMapRepository;
import com.daema.repository.StoreRepository;
import com.daema.response.enums.ServiceReturnMsgEnum;
import com.daema.response.exception.DataNotFoundException;
import com.daema.response.exception.ProcessErrorException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class SaleStoreMgmtService {

	private final StoreRepository storeRepository;

	private final StoreMapRepository storeMapRepository;

	private final OrganizationMgmtService organizationMgmtService;

	private final AuthenticationUtil authenticationUtil;

	public SaleStoreMgmtService(StoreRepository storeRepository, StoreMapRepository storeMapRepository, OrganizationMgmtService organizationMgmtService, AuthenticationUtil authenticationUtil) {
		this.storeRepository = storeRepository;
		this.storeMapRepository = storeMapRepository;
		this.organizationMgmtService = organizationMgmtService;
		this.authenticationUtil = authenticationUtil;
	}

	public ResponseDto<SaleStoreMgmtDto> getStoreList(SaleStoreMgmtRequestDto requestDto) {

		requestDto.setParentStoreId(authenticationUtil.getTargetStoreId(requestDto.getParentStoreId()));

		Page<Store> dataList = storeRepository.getSearchPage(requestDto);

		return new ResponseDto(SaleStoreMgmtDto.class, dataList);
	}

	//TODO 회원가입 로직으로 변경해야 함
	@Transactional
	public void insertStoreAndUserAndStoreMap(SaleStoreUserWrapperDto wrapperDto) {

		long resultStoreId = insertStore(wrapperDto.getSaleStore());

		long resultOrgId = organizationMgmtService.insertOrgnzt(OrganizationMgmtDto.builder()
				.orgName(Constants.ORGANIZATION_DEFAULT_GROUP_NAME)
				.parentOrgId(0)
				.storeId(resultStoreId).build());

		OrganizationMemberDto memberDto = wrapperDto.getMember();
		memberDto.setStoreId(resultStoreId);
		memberDto.setOrgId(resultOrgId);
		memberDto.setUserStatus(String.valueOf(StatusEnum.USER_APPROVAL.getStatusCode()));

		organizationMgmtService.insertUser(memberDto);

		if(wrapperDto.getParentStoreId() > 0){
			insertStoreMap(wrapperDto);
		}
	}

	public long insertStore(SaleStoreMgmtDto saleStoreMgmtDto) {
		return storeRepository.save(
                Store.builder()
						.storeId(saleStoreMgmtDto.getStoreId())
                        .storeName(saleStoreMgmtDto.getSaleStoreName())
                        .telecom(saleStoreMgmtDto.getTelecom())
                        .bizNo(saleStoreMgmtDto.getBizNo())
                        .chargerPhone(saleStoreMgmtDto.getChargerPhone())
                        .returnZipCode(saleStoreMgmtDto.getReturnZipCode())
                        .returnAddr(saleStoreMgmtDto.getReturnAddr())
                        .returnAddrDetail(saleStoreMgmtDto.getReturnAddrDetail())
                        .useYn(saleStoreMgmtDto.getUseYn())
                        .regiDateTime(LocalDateTime.now())
                    .build()
        ).getStoreId();
	}

	public void insertStoreMap(SaleStoreUserWrapperDto wrapperDto) {
        StoreMap storeMap = storeMapRepository.findByStoreIdAndParentStoreId(wrapperDto.getMember().getStoreId(), wrapperDto.getParentStoreId());

        if(storeMap == null) {
			storeMapRepository.save(
					StoreMap.builder()
							.storeId(wrapperDto.getMember().getStoreId())
							.parentStoreId(wrapperDto.getParentStoreId())
							.useYn(wrapperDto.getSaleStore().getUseYn())
							.build()
			);
		}
	}

	@Transactional
	public void updateStoreInfo(SaleStoreUserWrapperDto wrapperDto) {

		wrapperDto.setParentStoreId(authenticationUtil.getTargetStoreId(wrapperDto.getParentStoreId()));

		Store store = storeRepository.findStoreInfo(wrapperDto.getParentStoreId()
				, wrapperDto.getSaleStore().getStoreId());

		if(store != null) {
			//TODO ifnull return 함수 추가
			store.setStoreId(wrapperDto.getSaleStore().getStoreId());
			store.setStoreName(wrapperDto.getSaleStore().getSaleStoreName());
			store.setTelecom(wrapperDto.getSaleStore().getTelecom());
			store.setBizNo(wrapperDto.getSaleStore().getBizNo());
			store.setChargerPhone(wrapperDto.getSaleStore().getChargerPhone());
			store.setReturnZipCode(wrapperDto.getSaleStore().getReturnZipCode());
			store.setReturnAddr(wrapperDto.getSaleStore().getReturnAddr());
			store.setReturnAddrDetail(wrapperDto.getSaleStore().getReturnAddrDetail());

			if(StringUtils.hasText(wrapperDto.getSaleStore().getUseYn())) {
				store.setUseYn(wrapperDto.getSaleStore().getUseYn());
			}
		}else{
			throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
		}
	}

	@Transactional
	public void deleteStore(ModelMap reqModelMap) {

		List<Number> delStoreIds = (ArrayList<Number>) reqModelMap.get("delStoreId");
		long parentStoreId = authenticationUtil.getTargetStoreId(Long.parseLong(String.valueOf(reqModelMap.get("parentStoreId"))));

		if(delStoreIds != null
			&& delStoreIds.size() > 0){

			delStoreIds.forEach(storeId -> storeMapRepository.deleteById(new StoreMapPK(storeId.longValue(), parentStoreId)));
		}else{
			throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
		}
	}

	@Transactional
	public void updateStoreUse(ModelMap reqModelMap) {

		long storeId = Long.parseLong(String.valueOf(reqModelMap.getAttribute("storeId")));
		long parentStoreId = authenticationUtil.getTargetStoreId(Long.parseLong(String.valueOf(reqModelMap.getAttribute("parentStoreId"))));
		String useYn = String.valueOf(reqModelMap.getAttribute("useYn"));

		StoreMap storeMap = storeMapRepository.findByStoreIdAndParentStoreId(storeId, parentStoreId);

		if(storeMap != null) {
			storeMap.updateUseYn(storeMap, useYn);
		}else{
			throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
		}
	}
}



































