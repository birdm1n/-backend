package com.daema.rest.wms.dto.response;

import com.daema.rest.commgmt.dto.GoodsMgmtDto;
import com.daema.rest.commgmt.dto.GoodsOptionDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceResponseDto {

	private Long dvcId;
	private String fullBarcode;
	private GoodsMgmtDto goodsMgmtDto;
	private GoodsOptionDto goodsOptionDto;
	private Long storeId;

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



































