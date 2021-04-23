package com.daema.rest.wms.service;

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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockMgmtService {

	private final StockRepository stockRepository;

	private final AuthenticationUtil authenticationUtil;

	public StockMgmtService(StockRepository stockRepository, AuthenticationUtil authenticationUtil) {
		this.stockRepository = stockRepository;
		this.authenticationUtil = authenticationUtil;
	}

	public StockMgmtResponseDto getStockList(StockRequestDto requestDto) {

		requestDto.setStoreId(authenticationUtil.getStoreId());

		List<StockListDto> stockList = stockRepository.getStockList(requestDto);

		StockMgmtResponseDto responseDto = new StockMgmtResponseDto();

		responseDto.setStockList(stockList.stream().map(StockMgmtDto::dtoToDto).collect(Collectors.toList()));

		return responseDto;
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



































