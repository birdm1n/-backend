package com.daema.core.wms.dto.response;

import com.daema.core.commgmt.dto.GoodsMgmtDto;
import com.daema.core.wms.dto.DeviceDto;
import com.daema.core.wms.dto.InStockMgmtDto;
import com.daema.core.wms.dto.StockMgmtDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceResponseDto {
//  사용하지 않음..
//	private Long dvcId;
//	private String rawBarcode;
//	private Long storeId;
	private DeviceDto deviceDto;
	private InStockMgmtDto inStockMgmtDto;
	private GoodsMgmtDto goodsMgmtDto;
	private StockMgmtDto stockMgmtDto;


/*
	public static DeviceResponseDto from(Device device) {
		return DeviceResponseDto.builder()
				.dvcId(device.getDvcId())
				.fullBarcode(device.getFullBarcode())
				.goodsOptionDto(GoodsOptionDto.from(device.getGoodsOption()))
				.storeId()
				.regiUserId(device.getRegiUserId().getSeq())
				.regiDateTime(device.getRegiDateTime())
				.updDateTime(device.getUpdDateTime())
				.updUserId(device.getRegiUserId().getSeq())
				.delYn(device.getDelYn())
			.build();
	}*/
}



































