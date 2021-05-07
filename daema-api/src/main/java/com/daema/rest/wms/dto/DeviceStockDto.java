package com.daema.rest.wms.dto;

import com.daema.wms.domain.MoveStock;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceStockDto {

	private Long moveStockId;
	private WmsEnum.MoveStockType moveStockType;
	private long regiUserId;
	private LocalDateTime regiDateTime;
	private Long dvcId;
	private Long prevStockId;
	private Long nextStockId;

	public static DeviceStockDto from(MoveStock moveStock) {
		return DeviceStockDto.builder()
				.moveStockId(moveStock.getMoveStockId())
				.moveStockType(moveStock.getMoveStockType())
				.regiUserId(moveStock.getRegiUserId())
				.regiDateTime(moveStock.getRegiDateTime())
				.dvcId(moveStock.getDevice().getDvcId())
				.prevStockId(moveStock.getPrevStock().getStockId())
				.nextStockId(moveStock.getNextStock().getStockId())
			.build();
	}
}



































