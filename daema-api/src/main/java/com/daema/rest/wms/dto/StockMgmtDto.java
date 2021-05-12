package com.daema.rest.wms.dto;

import com.daema.wms.domain.Stock;
import com.daema.wms.domain.dto.response.StockListDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class StockMgmtDto {

	private long stockId;
	private long parentStockId;
	private String stockName;
	private String chargerName;
	private String chargerPhone;
	private String delYn;
	private long storeId;
	private String stockType;
	private long regiStoreId;
	private long regiUserId;
	private LocalDateTime regiDateTime;
	private Long updUserId;
	private LocalDateTime updDateTime;
	private int depth;
	private String hierarchy;
	private int dvcCnt;

	private List<StockMgmtDto> children;

	public static StockMgmtDto from (Stock stock) {
		return StockMgmtDto.builder()
				.stockId(stock.getStockId())
				.parentStockId(stock.getParentStockId())
				.stockName(stock.getStockName())
				.chargerName(stock.getChargerName())
				.chargerPhone(stock.getChargerPhone())
				.storeId(stock.getStoreId())
				.stockType(stock.getStockType())
				.regiStoreId(stock.getRegiStoreId())
				.regiUserId(stock.getRegiUserId())
				.regiDateTime(stock.getRegiDateTime())
				.updUserId(stock.getUpdUserId())
				.updDateTime(stock.getUpdDateTime())
				.delYn(stock.getDelYn())
			.build();
	}

	public static StockMgmtDto dtoToDto (StockListDto stockListDto) {
		return StockMgmtDto.builder()
				.depth(stockListDto.getDepth())
				.stockId(stockListDto.getStockId())
				.parentStockId(stockListDto.getParentStockId())
				.storeId(stockListDto.getStoreId())
				.stockName(stockListDto.getStockName())
				.stockType(stockListDto.getStockType())
				.chargerName(stockListDto.getChargerName())
				.chargerPhone(stockListDto.getChargerPhone())
				.hierarchy(stockListDto.getHierarchy())
				.dvcCnt(stockListDto.getDvcCnt())
			.build();
	}
}



































