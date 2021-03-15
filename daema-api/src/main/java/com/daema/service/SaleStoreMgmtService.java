package com.daema.service;

import com.daema.response.enums.ServiceReturnMsgEnum;
import com.daema.domain.Store;
import com.daema.domain.StoreMap;
import com.daema.domain.User2;
import com.daema.dto.SaleStoreMgmtDto;
import com.daema.dto.SaleStoreMgmtRequestDto;
import com.daema.dto.SaleStoreUserWrapperDto;
import com.daema.dto.UserMgmtDto;
import com.daema.dto.common.ResponseDto;
import com.daema.repository.StoreMapRepository;
import com.daema.repository.StoreRepository;
import com.daema.repository.User2Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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
	public boolean insertStoreAndUserAndStoreMap(SaleStoreUserWrapperDto wrapperDto) throws Exception {

		boolean success = false;

		try {

			long resultStoreId = insertStore(wrapperDto.getSaleStore());

			UserMgmtDto userDto = wrapperDto.getUser();
			userDto.setStoreId(resultStoreId);

			insertUser(userDto);

			if(wrapperDto.getParentStoreId() > 0){
			    insertStoreMap(resultStoreId, wrapperDto.getParentStoreId());
			}

			success = true;

		}catch (Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}

		return success;
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
                        .useYn("Y")
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
                        .userStatus("Y")
                        .lastUpdDateTime(LocalDateTime.now())
                    .build()
        );
	}

	public String insertStoreMap(long resultStoreId, long parentStoreId) {
        StoreMap storeMap = storeMapRepository.findByStoreIdAndParentStoreId(resultStoreId, parentStoreId);

        if(!Optional.ofNullable(storeMap).isPresent()) {
			storeMapRepository.save(
					StoreMap.builder()
							.storeId(resultStoreId)
							.parentStoreId(parentStoreId)
							.useYn("Y")
							.build()
			);

			return ServiceReturnMsgEnum.SUCCESS.name();
		}else{
        	return ServiceReturnMsgEnum.IS_NOT_PRESENT.name();
		}
	}

	@Transactional
	public String updateStoreInfo(SaleStoreUserWrapperDto wrapperDto) {

		Store store = storeRepository.findStoreInfo(wrapperDto.getParentStoreId()
				, wrapperDto.getSaleStore().getStoreId());

		if(Optional.ofNullable(store).isPresent()) {

			store.setStoreId(wrapperDto.getSaleStore().getStoreId());
			store.setStoreName(wrapperDto.getSaleStore().getSaleStoreName());
			store.setTelecom(wrapperDto.getSaleStore().getTelecom());
			store.setBizNo(wrapperDto.getSaleStore().getBizNo());
			store.setChargerPhone(wrapperDto.getSaleStore().getChargerPhone());
			store.setReturnZipCode(wrapperDto.getSaleStore().getReturnZipCode());
			store.setReturnAddr(wrapperDto.getSaleStore().getReturnAddr());
			store.setReturnAddrDetail(wrapperDto.getSaleStore().getReturnAddrDetail());
			store.setUseYn("Y");

			return ServiceReturnMsgEnum.SUCCESS.name();

		}else{
			return ServiceReturnMsgEnum.IS_NOT_PRESENT.name();
		}
	}

	public String deleteStore(String id) {
		return "";
	}

	public String updateStoreUse(SaleStoreMgmtDto saleStoreMgmtDto) {
		return "";
	}
}



































