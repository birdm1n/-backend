package com.daema.rest.wms.dto;

import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.Store;
import com.daema.rest.commgmt.dto.GoodsOptionDto;
import com.daema.rest.commgmt.dto.StoreDto;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDto {

	private Long dvcId;
	private WmsEnum.BarcodeType barcodeType;
	private String fullBarcode;
	private GoodsOptionDto goodsOptionDto;
	private StoreDto storeDto;
	private Long regiUserId;
	private LocalDateTime regiDateTime;
	private LocalDateTime updDateTime;
	private Long updUserId;
	private String delYn;

	public static DeviceDto from(Device device) {
		return DeviceDto.builder()
				.dvcId(device.getDvcId())
				.barcodeType(device.getBarcodeType())
				.fullBarcode(device.getFullBarcode())
				.goodsOptionDto(GoodsOptionDto.from(device.getGoodsOption()))
				.storeDto(StoreDto.from(device.getStore()))
				.regiUserId(device.getRegiUserId().getSeq())
				.regiDateTime(device.getRegiDateTime())
				.updDateTime(device.getUpdDateTime())
				.updUserId(device.getRegiUserId().getSeq())
				.delYn(device.getDelYn())
			.build();
	}

	public static Device toEntity(DeviceDto deviceDto) {
		return Device.builder()
				.dvcId(deviceDto.getDvcId())
				.barcodeType(deviceDto.getBarcodeType())
				.fullBarcode(deviceDto.getFullBarcode())
				.goodsOption(GoodsOption.builder()
						.goodsOptionId(deviceDto.getGoodsOptionDto().getGoodsOptionId())
						.build())
				.store(Store.builder()
						.storeId(deviceDto.getStoreDto().getStoreId())
						.build())
			.build();
	}
}



































