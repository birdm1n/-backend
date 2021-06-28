package com.daema.core.wms.dto;

import com.daema.core.wms.domain.MoveStockAlarm;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoveStockAlarmDto {

	@ApiModelProperty(value = "재판매 마감일자", example = "0")
	private Integer resellDay;

	@ApiModelProperty(value = "미출고 시점", example = "0")
	private Integer undeliveredDay;

	private Long storeId;

	private Long memberSeq;

	public static MoveStockAlarmDto from(MoveStockAlarm moveStockAlarm) {
		return MoveStockAlarmDto.builder()
				.resellDay(moveStockAlarm.getResellDay())
				.undeliveredDay(moveStockAlarm.getUndeliveredDay())
				.build();
	}
}
