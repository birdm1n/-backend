package com.daema.rest.wms.dto;

import com.daema.base.domain.Member;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.InStock;
import com.daema.wms.domain.Provider;
import com.daema.wms.domain.Stock;
import com.daema.wms.domain.dto.response.StockListDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class InStockMgmtDto {

	private Long inStockId;
	private String inStockStatus;
	private int inStockAmt;
	private String inStockMemo;
	private LocalDateTime regiDateTime;
	private Member regiUserId;
	private LocalDateTime updDateTime;
	private Member updUserId;
	private String delYn;
//	private Long provId;
//	private Long stockId;
//	private Long dvcId;
	private Provider provider;
	private Stock stock;
	private Device device;

	public static InStockMgmtDto from (InStock inStock) {
		return InStockMgmtDto.builder()
				.inStockId(inStock.getInStockId())
				.inStockStatus(inStock.getInStockStatus())
				.inStockAmt(inStock.getInStockAmt())
				.inStockMemo(inStock.getInStockMemo())
				.regiDateTime(inStock.getRegiDateTime())
				.regiUserId(inStock.getRegiUserId())
				.updDateTime(inStock.getUpdDateTime())
				.updUserId(inStock.getUpdUserId())
				.delYn(inStock.getDelYn())
				.provider(inStock.getProvider())
				.stock(inStock.getStock())
				.device(inStock.getDevice())
			.build();
	}
}