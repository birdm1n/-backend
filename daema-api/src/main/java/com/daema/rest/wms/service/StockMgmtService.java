package com.daema.rest.wms.service;

import com.daema.commgmt.domain.Store;
import com.daema.commgmt.repository.StoreRepository;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.enums.StatusEnum;
import com.daema.rest.common.enums.TypeEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.StockMgmtDto;
import com.daema.rest.wms.dto.response.StockMgmtResponseDto;
import com.daema.wms.domain.Stock;
import com.daema.wms.domain.dto.request.StockRequestDto;
import com.daema.wms.domain.dto.response.StockListDto;
import com.daema.wms.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class StockMgmtService {

	private final StockRepository stockRepository;

	private final StoreRepository storeRepository;

	private final AuthenticationUtil authenticationUtil;

	public StockMgmtService(StockRepository stockRepository, StoreRepository storeRepository, AuthenticationUtil authenticationUtil) {
		this.stockRepository = stockRepository;
		this.storeRepository = storeRepository;
		this.authenticationUtil = authenticationUtil;
	}

	public StockMgmtResponseDto getStockList(StockRequestDto requestDto) {

		requestDto.setStoreId(authenticationUtil.getStoreId());

		StockMgmtResponseDto stockMgmtResponseDto = new StockMgmtResponseDto();
		
		List<StockListDto> dataList = stockRepository.getStockList(requestDto);
		List<StockMgmtDto> stockList = new ArrayList<>();

		for(Iterator<StockListDto> iterator = dataList.iterator(); iterator.hasNext();){

			StockListDto stockDto = iterator.next();

			if(StringUtils.countOccurrencesOf(stockDto.getHierarchy(), "/") == 1){

				stockList.add(stockList.size(), StockMgmtDto.dtoToDto(stockDto));

			}else if(StringUtils.countOccurrencesOf(stockDto.getHierarchy(), "/") == 2){

				addChildrenElementToOrgnzt(stockList.get(stockList.size() - 1), stockDto);

			}else if(StringUtils.countOccurrencesOf(stockDto.getHierarchy(), "/") == 3){

				addChildrenElementToOrgnzt(stockList.get(stockList.size() - 1).getChildren()
						.get(stockList.get(stockList.size() - 1).getChildren().size() - 1), stockDto);
			}
		}

		stockMgmtResponseDto.setStoreName(storeRepository.findById(requestDto.getStoreId()).orElseGet(Store::new).getStoreName());
		stockMgmtResponseDto.setStockList(stockList);

		return stockMgmtResponseDto;
	}

	private void addChildrenElementToOrgnzt(StockMgmtDto parent, StockListDto child){
		if(parent.getChildren() == null){
			parent.setChildren(new ArrayList<>());
		}

		parent.getChildren().add(StockMgmtDto.dtoToDto(child));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertStock(StockMgmtDto stockMgmtDto) {

		//내부 관리 창고는 현재 로그인 기준으로 storeId 설정
		if(TypeEnum.STOCK_TYPE_I.getStatusCode().equals(stockMgmtDto.getStockType())){
			stockMgmtDto.setStoreId(authenticationUtil.getStoreId());
		}

		if(stockMgmtDto.getRegiStoreId() == 0){
			stockMgmtDto.setRegiStoreId(authenticationUtil.getStoreId());
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
						.regiUserId(authenticationUtil.getMemberSeq())
						.regiDateTime(LocalDateTime.now())
						.updUserId(authenticationUtil.getMemberSeq())
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



































