package com.daema.rest.wms.dto;

import com.daema.wms.domain.Stock;
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
	private Stock parentStock;
	private String stockName;
	private String chargerName;
	private String chargerPhone;
	private String chargerPhone1;
	private String chargerPhone2;
	private String chargerPhone3;
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
				.parentStock(stock.getParentStock())
				.stockName(stock.getStockName())
				.chargerName(stock.getChargerName())
				.chargerPhone(stock.getChargerPhone())
				.chargerPhone1(stock.getChargerPhone1())
				.chargerPhone2(stock.getChargerPhone2())
				.chargerPhone3(stock.getChargerPhone3())
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
}



































