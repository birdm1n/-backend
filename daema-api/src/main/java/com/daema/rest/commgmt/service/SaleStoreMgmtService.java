package com.daema.rest.commgmt.service;

import com.daema.core.base.enums.StatusEnum;
import com.daema.core.base.enums.TypeEnum;
import com.daema.core.base.enums.UserRole;
import com.daema.core.commgmt.domain.FuncMgmt;
import com.daema.core.commgmt.domain.RoleMgmt;
import com.daema.core.commgmt.domain.Store;
import com.daema.core.commgmt.domain.StoreMap;
import com.daema.core.commgmt.domain.pk.StoreMapPK;
import com.daema.core.commgmt.dto.OrganizationMemberDto;
import com.daema.core.commgmt.dto.OrganizationMgmtDto;
import com.daema.core.commgmt.dto.SaleStoreMgmtDto;
import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;
import com.daema.core.commgmt.dto.request.SaleStoreUserWrapperDto;
import com.daema.core.commgmt.repository.FuncMgmtRepository;
import com.daema.core.commgmt.repository.StoreMapRepository;
import com.daema.core.commgmt.repository.StoreRepository;
import com.daema.rest.base.dto.response.ResponseDto;
import com.daema.rest.common.consts.Constants;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.common.util.JwtUtil;
import com.daema.rest.wms.service.MoveStockMgmtService;
import com.daema.rest.wms.service.StockMgmtService;
import com.daema.core.wms.dto.MoveStockAlarmDto;
import com.daema.core.wms.dto.StockMgmtDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
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

	private final StockMgmtService stockMgmtService;
	private final MoveStockMgmtService moveStockMgmtService;

	private final AuthenticationUtil authenticationUtil;

	private final JwtUtil jwtUtil;

	public SaleStoreMgmtService(StoreRepository storeRepository, StoreMapRepository storeMapRepository, OrganizationMgmtService organizationMgmtService, StockMgmtService stockMgmtService
			, RoleFuncMgmtService roleFuncMgmtService, FuncMgmtRepository funcMgmtRepository, MoveStockMgmtService moveStockMgmtService, AuthenticationUtil authenticationUtil, JwtUtil jwtUtil) {
		this.storeRepository = storeRepository;
		this.storeMapRepository = storeMapRepository;
		this.organizationMgmtService = organizationMgmtService;
		this.roleFuncMgmtService = roleFuncMgmtService;
		this.funcMgmtRepository = funcMgmtRepository;
		this.stockMgmtService = stockMgmtService;
		this.moveStockMgmtService = moveStockMgmtService;
		this.authenticationUtil = authenticationUtil;
		this.jwtUtil = jwtUtil;
	}

	public ResponseDto<SaleStoreMgmtDto> getStoreList(ComMgmtRequestDto requestDto) {

		requestDto.setParentStoreId(authenticationUtil.getTargetStoreId(requestDto.getParentStoreId()));

		Page<Store> dataList = storeRepository.getSearchPage(requestDto);

		return new ResponseDto(SaleStoreMgmtDto.class, dataList);
	}

	/**
	 * ??????????????? ??????
	 * 1. ?????? ????????? ?????? ??? ?????? ??????
	 * 2. ?????? ?????? ??????
	 * 3. ????????? (ROLE_MANAGER) ??????
	 * 4. ROLE_MANAGER ??? ???????????? ????????? ?????? ??????
	 * 5. ????????? ?????? ?????? ????????? ???????????? ?????? ????????? ?????? ??????(store_map) ??? ?????? ??????
	 * @param wrapperDto
	 * @return
	 */
	@Transactional
	public void insertStoreAndUserAndStoreMap(SaleStoreUserWrapperDto wrapperDto, HttpServletRequest request) {

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

		//resultStoreId ??? ????????? role ??? ????????? ??????????????? ??????
		List<RoleMgmt> roleMgmtList = roleFuncMgmtService.getRoleList(resultStoreId);

		if(CommonUtil.isNotEmptyList(roleMgmtList)) {
			memberDto.setRoleIds(roleMgmtList.stream().map(RoleMgmt::getRoleId).toArray(Long[]::new));
		}

		//????????? ?????? ??? ??? ??????
		long memberSeq = organizationMgmtService.insertUser(memberDto, request, false);

		//?????? ?????? ?????? ??????
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
			roleFuncMgmtService.setFuncRoleMapInfo(roleFuncList, resultStoreId);
		}

		//??? ?????? ??????
		stockMgmtService.insertStock(
				StockMgmtDto.builder()
						.stockId(0)
						.stockName(wrapperDto.getSaleStore().getSaleStoreName())
						.parentStock(null)
						.storeId(resultStoreId)
						.stockType(TypeEnum.STOCK_TYPE_I.getStatusCode())
						.regiStoreId(resultStoreId)
						.chargerName(wrapperDto.getSaleStore().getChargerName())
						.chargerPhone(
								wrapperDto.getSaleStore().getChargerPhone1()
								.concat(wrapperDto.getSaleStore().getChargerPhone2())
								.concat(wrapperDto.getSaleStore().getChargerPhone3())
						)
						.chargerPhone1(wrapperDto.getSaleStore().getChargerPhone1())
						.chargerPhone2(wrapperDto.getSaleStore().getChargerPhone2())
						.chargerPhone3(wrapperDto.getSaleStore().getChargerPhone3())
						.delYn(StatusEnum.FLAG_N.getStatusMsg())
						.regiUserId(memberSeq)
						.regiDateTime(LocalDateTime.now())
						.updUserId(memberSeq)
						.updDateTime(LocalDateTime.now())
					.build()
		);


		//?????? ?????? ???????????? ????????? ??????
		MoveStockAlarmDto moveStockAlarmDto = new MoveStockAlarmDto();
		moveStockAlarmDto.setStoreId(resultStoreId);
		moveStockAlarmDto.setResellDay(30);
		moveStockAlarmDto.setUndeliveredDay(15);
		moveStockAlarmDto.setMemberSeq(memberSeq);

		moveStockMgmtService.setLongTimeStoreStockAlarm(moveStockAlarmDto);


		//?????? ????????? ?????? ????????? / ????????? ?????? ????????????
		String accessToken = jwtUtil.getAccessTokenFromHeader(request, JwtUtil.AUTHORIZATION);

		if(StringUtils.hasText(accessToken)){
			Long parentStoreId = (Long) jwtUtil.getClaim(accessToken, "sId", Long.class);

			if(parentStoreId != null
					&& parentStoreId > 0) {
				wrapperDto.setParentStoreId(parentStoreId);
				wrapperDto.getMember().setSeq(memberSeq);
				wrapperDto.getMember().setStoreId(resultStoreId);
				insertStoreMap(wrapperDto);
			}
		}
	}

	@Transactional
	public long insertStore(SaleStoreMgmtDto saleStoreMgmtDto) {
		return storeRepository.save(
                Store.builder()
						.storeId(saleStoreMgmtDto.getStoreId())
                        .storeName(saleStoreMgmtDto.getSaleStoreName())
                        .telecom(saleStoreMgmtDto.getTelecom())
						.ceoName(saleStoreMgmtDto.getCeoName())
                        .bizNo(saleStoreMgmtDto.getBizNo())
                        .chargerPhone(
                        		saleStoreMgmtDto.getChargerPhone1()
								.concat(saleStoreMgmtDto.getChargerPhone2())
								.concat(saleStoreMgmtDto.getChargerPhone3())
						)
                        .chargerPhone1(saleStoreMgmtDto.getChargerPhone1())
                        .chargerPhone2(saleStoreMgmtDto.getChargerPhone2())
                        .chargerPhone3(saleStoreMgmtDto.getChargerPhone3())
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

		wrapperDto.setParentStoreId(wrapperDto.getParentStoreId() == 0 ?
						authenticationUtil.getStoreId() : wrapperDto.getParentStoreId());

        StoreMap storeMap = storeMapRepository.findById(new StoreMapPK(wrapperDto.getMember().getStoreId(), wrapperDto.getParentStoreId())).orElse(null);

        if(storeMap == null) {
			storeMapRepository.save(
					StoreMap.builder()
							.storeId(wrapperDto.getMember().getStoreId())
							.parentStoreId(wrapperDto.getParentStoreId())
							.useYn(StatusEnum.FLAG_Y.getStatusMsg())
							.build()
			);

			Store store = storeRepository.findById(wrapperDto.getMember().getStoreId()).orElse(null);

			wrapperDto.getMember()
					.setSeq(wrapperDto.getMember().getSeq() == 0 ?
							authenticationUtil.getMemberSeq() : wrapperDto.getMember().getSeq());

			//?????? ?????? ??????
			stockMgmtService.insertStock(
					StockMgmtDto.builder()
							.stockId(0)
							.stockName(store.getStoreName())
							.parentStock(null)
							.storeId(wrapperDto.getMember().getStoreId())
							.stockType(TypeEnum.STOCK_TYPE_S.getStatusCode())
							.regiStoreId(wrapperDto.getParentStoreId())
							.chargerName(store.getChargerName())
							.chargerPhone(
									store.getChargerPhone1()
									.concat(store.getChargerPhone2())
									.concat(store.getChargerPhone3())
							)
							.chargerPhone1(store.getChargerPhone1())
							.chargerPhone2(store.getChargerPhone2())
							.chargerPhone3(store.getChargerPhone3())
							.delYn(StatusEnum.FLAG_N.getStatusMsg())
							.regiUserId(wrapperDto.getMember().getSeq())
							.regiDateTime(LocalDateTime.now())
							.updUserId(wrapperDto.getMember().getSeq())
							.updDateTime(LocalDateTime.now())
						.build()
			);
		}
	}

	@Transactional
	public void insertStockInfo(){

	}

	@Transactional
	public void updateStoreInfo(SaleStoreUserWrapperDto wrapperDto) {

		wrapperDto.setParentStoreId(authenticationUtil.getTargetStoreId(wrapperDto.getParentStoreId()));

		Store store = storeRepository.findStoreInfo(wrapperDto.getParentStoreId()
				, wrapperDto.getSaleStore().getStoreId());

		if(store != null) {
			//TODO ifnull return ?????? ??????
			store.setStoreId(wrapperDto.getSaleStore().getStoreId());
			store.setStoreName(wrapperDto.getSaleStore().getSaleStoreName());
			store.setTelecom(wrapperDto.getSaleStore().getTelecom());
			store.setCeoName(wrapperDto.getSaleStore().getCeoName());
			store.setBizNo(wrapperDto.getSaleStore().getBizNo());
			store.setChargerPhone(
					wrapperDto.getSaleStore().getChargerPhone1()
					.concat(wrapperDto.getSaleStore().getChargerPhone2())
					.concat(wrapperDto.getSaleStore().getChargerPhone3())
			);
			store.setChargerPhone1(wrapperDto.getSaleStore().getChargerPhone1());
			store.setChargerPhone2(wrapperDto.getSaleStore().getChargerPhone2());
			store.setChargerPhone3(wrapperDto.getSaleStore().getChargerPhone3());
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

		StoreMap storeMap = storeMapRepository.findById(new StoreMapPK(storeId, parentStoreId)).orElse(null);

		if(storeMap != null) {
			storeMap.updateUseYn(storeMap, useYn);
		}else{
			throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
		}
	}
}



































