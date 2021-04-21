package com.daema.rest.commgmt.service;

import com.daema.base.enums.UserRole;
import com.daema.commgmt.domain.FuncMgmt;
import com.daema.commgmt.domain.RoleMgmt;
import com.daema.commgmt.domain.Store;
import com.daema.commgmt.domain.StoreMap;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.daema.commgmt.domain.pk.StoreMapPK;
import com.daema.commgmt.repository.FuncMgmtRepository;
import com.daema.commgmt.repository.StoreMapRepository;
import com.daema.commgmt.repository.StoreRepository;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.commgmt.dto.OrganizationMemberDto;
import com.daema.rest.commgmt.dto.OrganizationMgmtDto;
import com.daema.rest.commgmt.dto.SaleStoreMgmtDto;
import com.daema.rest.commgmt.dto.request.SaleStoreUserWrapperDto;
import com.daema.rest.common.Constants;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.enums.StatusEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
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

	private final RoleFuncMgmtService roleFuncMgmtService;

	private final FuncMgmtRepository funcMgmtRepository;

	private final AuthenticationUtil authenticationUtil;

	public SaleStoreMgmtService(StoreRepository storeRepository, StoreMapRepository storeMapRepository, OrganizationMgmtService organizationMgmtService
								,RoleFuncMgmtService roleFuncMgmtService, FuncMgmtRepository funcMgmtRepository ,AuthenticationUtil authenticationUtil) {
		this.storeRepository = storeRepository;
		this.storeMapRepository = storeMapRepository;
		this.organizationMgmtService = organizationMgmtService;
		this.roleFuncMgmtService = roleFuncMgmtService;
		this.funcMgmtRepository = funcMgmtRepository;
		this.authenticationUtil = authenticationUtil;
	}

	public ResponseDto<SaleStoreMgmtDto> getStoreList(ComMgmtRequestDto requestDto) {

		requestDto.setParentStoreId(authenticationUtil.getTargetStoreId(requestDto.getParentStoreId()));

		Page<Store> dataList = storeRepository.getSearchPage(requestDto);

		return new ResponseDto(SaleStoreMgmtDto.class, dataList);
	}

	/**
	 * 회원가입시 사용
	 * 1. 신규 영업점 등록
	 * 2. 기본 그룹 생성
	 * 3. 사용자 (ROLE_MANAGER) 등록
	 * 4. ROLE_MANAGER 에 해당하는 사용자 기능 부여
	 * 5. 관리점 링크 통해 가입한 영업점은 상위 관리점 정보 등록(store_map)
	 * @param wrapperDto
	 * @return
	 */
	@Transactional
	public void insertStoreAndUserAndStoreMap(SaleStoreUserWrapperDto wrapperDto) {

		wrapperDto.getSaleStore().setUseYn(StatusEnum.FLAG_Y.getStatusMsg());

		long resultStoreId = insertStore(wrapperDto.getSaleStore());

		long resultOrgId = organizationMgmtService.insertOrgnzt(OrganizationMgmtDto.builder()
				.orgName(Constants.ORGANIZATION_DEFAULT_GROUP_NAME)
				.parentOrgId(0)
				.storeId(resultStoreId).build());

		OrganizationMemberDto memberDto = wrapperDto.getMember();
		memberDto.setStoreId(resultStoreId);
		memberDto.setOrgId(resultOrgId);
		memberDto.setUserStatus(String.valueOf(StatusEnum.USER_APPROVAL.getStatusCode()));
		memberDto.setRole(UserRole.ROLE_MANAGER);

		//resultStoreId 로 등록된 role 은 없으나 형식적으로 던짐
		List<RoleMgmt> roleMgmtList = roleFuncMgmtService.getRoleList(resultStoreId);

		if(CommonUtil.isNotEmptyList(roleMgmtList)) {
			memberDto.setRoleIds(roleMgmtList.stream().map(RoleMgmt::getRoleId).toArray(Integer[]::new));
		}

		//사용자 추가 및 롤 등록
		organizationMgmtService.insertUser(memberDto);

		//롤과 기능 맵핑 등록
		List<FuncMgmt> funcList = funcMgmtRepository.findAllByRoleContainingOrderByGroupIdAscRoleAscOrderNumAsc(UserRole.ROLE_MANAGER.name());

		List<ModelMap> roleFuncList = new ArrayList<>();

		funcList.forEach(
				funcMgmt -> {
					roleMgmtList.forEach(
							roleMgmt -> {
								ModelMap modelMap = new ModelMap();
								modelMap.put("funcId", funcMgmt.getFuncId());
								modelMap.put("roleId", roleMgmt.getRoleId());
								modelMap.put("mapYn", StatusEnum.FLAG_Y.getStatusMsg());

								roleFuncList.add(modelMap);
							}
					);
				}
		);

		if(CommonUtil.isNotEmptyList(roleFuncList)) {
			roleFuncMgmtService.setFuncRoleMapInfo(roleFuncList);
		}

		if(wrapperDto.getParentStoreId() > 0){
			insertStoreMap(wrapperDto);
		}
	}

	@Transactional
	public long insertStore(SaleStoreMgmtDto saleStoreMgmtDto) {
		return storeRepository.save(
                Store.builder()
						.storeId(saleStoreMgmtDto.getStoreId())
                        .storeName(saleStoreMgmtDto.getSaleStoreName())
                        .telecom(saleStoreMgmtDto.getTelecom())
                        .bizNo(saleStoreMgmtDto.getBizNo())
                        .chargerPhone(saleStoreMgmtDto.getChargerPhone())
                        .chargerName(saleStoreMgmtDto.getChargerName())
                        .chargerEmail(saleStoreMgmtDto.getChargerEmail())
                        .returnZipCode(saleStoreMgmtDto.getReturnZipCode())
                        .returnAddr(saleStoreMgmtDto.getReturnAddr())
                        .returnAddrDetail(saleStoreMgmtDto.getReturnAddrDetail())
                        .useYn(saleStoreMgmtDto.getUseYn())
                        .regiDateTime(LocalDateTime.now())
                    .build()
        ).getStoreId();
	}

	@Transactional
	public void insertStoreMap(SaleStoreUserWrapperDto wrapperDto) {
        StoreMap storeMap = storeMapRepository.findByStoreIdAndParentStoreId(wrapperDto.getMember().getStoreId(), wrapperDto.getParentStoreId());

        if(storeMap == null) {
			storeMapRepository.save(
					StoreMap.builder()
							.storeId(wrapperDto.getMember().getStoreId())
							.parentStoreId(wrapperDto.getParentStoreId())
							.useYn(StatusEnum.FLAG_Y.getStatusMsg())
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
			store.setChargerName(wrapperDto.getSaleStore().getChargerName());
			store.setChargerEmail(wrapperDto.getSaleStore().getChargerEmail());
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



































