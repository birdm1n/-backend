package com.daema.rest.wms.dto;

import com.daema.wms.domain.DeviceStock;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceStockDto {

	private Long dvcStockId;
	private WmsEnum.DvcStockType dvcStockType;
	private long regiUserId;
	private LocalDateTime regiDateTime;
	private Long dvcId;
	private Long prevStockId;
	private Long nextStockId;

	public static DeviceStockDto from(DeviceStock deviceStock) {
		return DeviceStockDto.builder()
				.dvcStockId(deviceStock.getDvcStockId())
				.dvcStockType(deviceStock.getDvcStockType())
				.regiUserId(deviceStock.getRegiUserId())
				.regiDateTime(deviceStock.getRegiDateTime())
				.dvcId(deviceStock.getDevice().getDvcId())
				.prevStockId(deviceStock.getPrevStock().getStockId())
				.nextStockId(deviceStock.getNextStock().getStockId())
			.build();
	}
}



































