package com.daema.rest.wms.service;

import com.daema.base.enums.StatusEnum;
import com.daema.base.enums.TypeEnum;
import com.daema.base.repository.MemberRepository;
import com.daema.commgmt.domain.Store;
import com.daema.commgmt.repository.StoreRepository;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.StockMgmtDto;
import com.daema.rest.wms.dto.response.SearchMatchResponseDto;
import com.daema.rest.wms.dto.response.StockMgmtResponseDto;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.Stock;
import com.daema.wms.domain.dto.request.StockRequestDto;
import com.daema.wms.domain.dto.response.SelectStockDto;
import com.daema.wms.domain.dto.response.StockListDto;
import com.daema.wms.repository.DeviceRepository;
import com.daema.wms.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StockMgmtService {

	private final StockRepository stockRepository;

	private final StoreRepository storeRepository;

	private final MemberRepository memberRepository;

	private final DeviceRepository deviceRepository;

	private final AuthenticationUtil authenticationUtil;


	public StockMgmtResponseDto getStockList(StockRequestDto requestDto) {

		requestDto.setStoreId(authenticationUtil.getStoreId());

		StockMgmtResponseDto stockMgmtResponseDto = new StockMgmtResponseDto();

		HashMap<String, List> stockDeviceListMap = stockRepository.getStockAndDeviceList(requestDto);

		List<StockListDto> dataList = stockDeviceListMap.get("stockList");
		List<StockMgmtDto> stockList = new ArrayList<>();

		for(Iterator<StockListDto> iterator = dataList.iterator(); iterator.hasNext();){

			StockListDto stockDto = iterator.next();

			if(StringUtils.countOccurrencesOf(stockDto.getHierarchy(), "/") == 1){

				stockList.add(stockList.size(), StockMgmtDto.dtoToDto(stockDto));

			}else if(StringUtils.countOccurrencesOf(stockDto.getHierarchy(), "/") == 2){

				addChildrenElementToStock(stockList.get(stockList.size() - 1), stockDto);

			}else if(StringUtils.countOccurrencesOf(stockDto.getHierarchy(), "/") == 3){

				addChildrenElementToStock(stockList.get(stockList.size() - 1).getChildren()
						.get(stockList.get(stockList.size() - 1).getChildren().size() - 1), stockDto);
			}
		}

		stockMgmtResponseDto.setStoreName(storeRepository.findById(requestDto.getStoreId()).orElseGet(Store::new).getStoreName());
		stockMgmtResponseDto.setStockList(stockList);
		stockMgmtResponseDto.setStockDeviceList(stockDeviceListMap.get("stockDeviceList"));

		//TODO dvcCnt 각 보유처별 기기 카운트. 하위 보유처 포함


		return stockMgmtResponseDto;
	}

	private void addChildrenElementToStock(StockMgmtDto parent, StockListDto child){
		if(parent.getChildren() == null){
			parent.setChildren(new ArrayList<>());
		}

		parent.getChildren().add(StockMgmtDto.dtoToDto(child));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertStock(StockMgmtDto stockMgmtDto) {

		//내부 관리 창고는 현재 로그인 기준으로 storeId 설정
		if(TypeEnum.STOCK_TYPE_I.getStatusCode().equals(stockMgmtDto.getStockType())){
			//관리점(영업점) 회원 가입시, 내부 창고가 생성되며 로그인 이전 상태이므로 예외처리 추가
			if(!authenticationUtil.hasRole("ROLE_ANONYMOUS")
				|| authenticationUtil.getStoreId() > 0) {
				stockMgmtDto.setStoreId(authenticationUtil.getStoreId());
			}
		}

		if(stockMgmtDto.getRegiStoreId() == 0){
			stockMgmtDto.setRegiStoreId(authenticationUtil.getStoreId());
		}

		if(stockMgmtDto.getRegiUserId() == 0){
			stockMgmtDto.setRegiUserId(authenticationUtil.getMemberSeq());
			stockMgmtDto.setUpdUserId(authenticationUtil.getMemberSeq());
		}

		stockRepository.save(
				Stock.builder()
						.stockId(stockMgmtDto.getStockId())
						.stockName(stockMgmtDto.getStockName())
						.parentStockId(stockMgmtDto.getParentStockId())
						.storeId(stockMgmtDto.getStoreId())
						.stockType(stockMgmtDto.getStockType())
						.regiStoreId(stockMgmtDto.getRegiStoreId())
						.chargerName(stockMgmtDto.getChargerName())
						.chargerPhone(stockMgmtDto.getChargerPhone())
						.delYn(StatusEnum.FLAG_N.getStatusMsg())
						.regiUserId(stockMgmtDto.getRegiUserId())
						.regiDateTime(LocalDateTime.now())
						.updUserId(stockMgmtDto.getUpdUserId())
						.updDateTime(LocalDateTime.now())
					.build()
		);
	}

	@Transactional
	public void updateStock(StockMgmtDto stockMgmtDto) {

		Stock stock = stockRepository.findById(stockMgmtDto.getStockId()).orElse(null);
		long storeId = authenticationUtil.getStoreId();

		if(stock != null
				&& stock.getRegiStoreId() == storeId) {
			stock.setStockName(stockMgmtDto.getStockName());
			stock.setChargerName(stockMgmtDto.getChargerName());
			stock.setChargerPhone(stockMgmtDto.getChargerPhone());
		}else{
			throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
		}
	}
	@Transactional(readOnly = true)
    public List<SelectStockDto> selectStockList(Integer telecom) {
		long storeId = authenticationUtil.getStoreId();
		return stockRepository.selectStockList(storeId, telecom);
    }

	@Transactional(readOnly = true)
	public List<SelectStockDto> innerStockList() {
		long storeId = authenticationUtil.getStoreId();
		return stockRepository.innerStockList(storeId);
	}
	@Transactional(readOnly = true)
	public SearchMatchResponseDto getDeviceStock(String fullBarcode) {
		long storeId = authenticationUtil.getStoreId();
		Store store = Store.builder().storeId(storeId).build();
		//device 테이블에 중복된 기기가 있는지 확인
		Device deviceEntity = deviceRepository.findByFullBarcodeAndStoreAndDelYn(fullBarcode, store, StatusEnum.FLAG_N.getStatusMsg());
		if(deviceEntity == null){
			return null;
		}

		Stock stockEntity = deviceEntity.getStoreStock().getNextStock(); //현재 보유처
		SearchMatchResponseDto responseDto = null;
		if (stockEntity != null) {
			responseDto = SearchMatchResponseDto
					.builder()
					.stockId(stockEntity.getStockId())
					.stockName(stockEntity.getStockName())
					.build();
		}
		return responseDto;
	}


/*

	@Transactional
	public void deleteStock(StockMgmtDto stockMgmtDto) {

		//TODO
		//하위 창고의 del_yn 체크
		//하위 창고에 기기 존재 여부 체크. 기기 테이블 나온 뒤 작업 가능

		Stock stock = stockRepository.findById(stockMgmtDto.getStockId()).orElse(null);
		long storeId = authenticationUtil.getStoreId();

		if(stock != null
				&& stock.getRegiStoreId() == storeId) {
			stock.setDelYn(StatusEnum.FLAG_Y.getStatusMsg());

			List<Member> membersList = memberRepository.findByOrgId(orgnzt.getOrgId());

			if(CommonUtil.isNotEmptyList(membersList)) {
				Stock orgnztBasicData = stockRepository.findByStoreIdAndOrgName(orgnzt.getStoreId(), Constants.ORGANIZATION_DEFAULT_GROUP_NAME);

				if (orgnztBasicData != null) {
					Optional.ofNullable(membersList).orElseGet(Collections::emptyList).forEach(members -> {
						members.updateStockId(members, orgnztBasicData.getOrgId());
					});
				}
			}
		}else{
			throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
		}
	}
*/

}



































