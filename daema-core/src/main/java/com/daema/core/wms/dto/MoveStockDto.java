package com.daema.core.wms.dto;

import com.daema.core.commgmt.dto.MemberMgmtDto;
import com.daema.core.wms.domain.MoveStock;
import com.daema.core.wms.domain.enums.WmsEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoveStockDto {

	private Long moveStockId;
	private WmsEnum.MoveStockType moveStockType;
	private MemberMgmtDto regiUserId;
	private LocalDateTime regiDateTime;
	private Long dvcId;
	private Long prevStockId;
	private Long nextStockId;

	public static MoveStockDto from(MoveStock moveStock) {
		return MoveStockDto.builder()
				.moveStockId(moveStock.getMoveStockId())
				.moveStockType(moveStock.getMoveStockType())
				.regiUserId(MemberMgmtDto.from(moveStock.getRegiUserId()))
				.regiDateTime(moveStock.getRegiDateTime())
				.dvcId(moveStock.getDevice().getDvcId())
				.prevStockId(moveStock.getPrevStock().getStockId())
				.nextStockId(moveStock.getNextStock().getStockId())
			.build();
	}
}



































