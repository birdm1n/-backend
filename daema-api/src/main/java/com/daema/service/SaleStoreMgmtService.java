package com.daema.service;

import com.daema.domain.Store;
import com.daema.domain.StoreMap;
import com.daema.domain.User2;
import com.daema.domain.pk.StoreMapPK;
import com.daema.dto.SaleStoreMgmtDto;
import com.daema.dto.SaleStoreMgmtRequestDto;
import com.daema.dto.SaleStoreUserWrapperDto;
import com.daema.dto.UserMgmtDto;
import com.daema.dto.common.ResponseDto;
import com.daema.repository.StoreMapRepository;
import com.daema.repository.StoreRepository;
import com.daema.repository.User2Repository;
import com.daema.response.enums.ServiceReturnMsgEnum;
import com.daema.response.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	private final User2Repository user2Repository;

	private final StoreMapRepository storeMapRepository;

	public SaleStoreMgmtService(StoreRepository storeRepository, User2Repository user2Repository, StoreMapRepository storeMapRepository) {
		this.storeRepository = storeRepository;
		this.user2Repository = user2Repository;
		this.storeMapRepository = storeMapRepository;
	}

	public ResponseDto<SaleStoreMgmtDto> getStoreList(SaleStoreMgmtRequestDto requestDto) {

		PageRequest pageable = PageRequest.of(requestDto.getPageNo(), requestDto.getPerPageCnt());

		Page<Store> dataList = storeRepository.getSearchPage(pageable);

		return new ResponseDto(SaleStoreMgmtDto.class, dataList);
	}

	public SaleStoreMgmtDto getStoreDetail(long storeId) {

		Store store = storeRepository.findById(storeId).orElseGet(() -> null);

		return store != null ? SaleStoreMgmtDto.from(store) : null;
	}

	@Transactional
	public void insertStoreAndUserAndStoreMap(SaleStoreUserWrapperDto wrapperDto) {

		long resultStoreId = insertStore(wrapperDto.getSaleStore());

		UserMgmtDto userDto = wrapperDto.getUser();
		userDto.setStoreId(resultStoreId);

		insertUser(userDto);

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

	public void insertUser(UserMgmtDto userMgmtDto) {
		user2Repository.save(
                User2.builder()
						.userId(userMgmtDto.getUserId())
                        .email(userMgmtDto.getEmail())
                        .userPw(userMgmtDto.getUserPw())
                        .userName(userMgmtDto.getUserName())
                        .userPhone(userMgmtDto.getUserPhone())
                        .storeId(userMgmtDto.getStoreId())
                        .orgnztId(userMgmtDto.getOrgnztId())
                        .userStatus(userMgmtDto.getUserStatus())
                        .lastUpdDateTime(LocalDateTime.now())
                    .build()
        );
	}

	public void insertStoreMap(SaleStoreUserWrapperDto wrapperDto) {
        StoreMap storeMap = storeMapRepository.findByStoreIdAndParentStoreId(wrapperDto.getUser().getStoreId(), wrapperDto.getParentStoreId());

        if(storeMap == null) {
			storeMapRepository.save(
					StoreMap.builder()
							.storeId(wrapperDto.getUser().getStoreId())
							.parentStoreId(wrapperDto.getParentStoreId())
							.useYn(wrapperDto.getSaleStore().getUseYn())
							.build()
			);
		}
	}

	@Transactional
	public void updateStoreInfo(SaleStoreUserWrapperDto wrapperDto) {

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
		long parentStoreId = Long.parseLong(String.valueOf(reqModelMap.get("parentStoreId")));

		if(delStoreIds != null
			&& delStoreIds.size() > 0){

			delStoreIds.forEach(storeId -> storeMapRepository.deleteById(new StoreMapPK(storeId.longValue(), parentStoreId)));
		}else{
			throw new IllegalArgumentException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
		}
	}

	@Transactional
	public void updateStoreUse(ModelMap reqModelMap) {

		long storeId = Long.parseLong(String.valueOf(reqModelMap.getAttribute("storeId")));
		long parentStoreId = Long.parseLong(String.valueOf(reqModelMap.getAttribute("parentStoreId")));
		String useYn = String.valueOf(reqModelMap.getAttribute("useYn"));

		StoreMap storeMap = storeMapRepository.findByStoreIdAndParentStoreId(storeId, parentStoreId);

		if(storeMap != null) {
			storeMap.updateUseYn(storeMap, useYn);
		}else{
			throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
		}
	}
}



































