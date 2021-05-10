package com.daema.rest.wms.dto.request;

import com.daema.rest.wms.dto.DeviceStatusDto;
import com.daema.wms.domain.enums.WmsEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnStockReqDto {

	@ApiModelProperty(value = "기기ID", required = true)
	private Long dvcId;

	@ApiModelProperty(value = "입고상태")
	private WmsEnum.InStockStatus returnStockStatus;

	@ApiModelProperty(value = "반품비")
	private Integer returnStockAmt;

	@ApiModelProperty(value = "메모")
	private String returnStockMemo;

	@ApiModelProperty(value = "제품상태")
	private DeviceStatusDto returnDeviceStatus;

	@ApiModelProperty(value = "이전 보유처")
	private Long prevStockId;

}



































