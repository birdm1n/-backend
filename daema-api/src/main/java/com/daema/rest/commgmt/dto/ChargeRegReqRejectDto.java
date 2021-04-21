package com.daema.rest.commgmt.dto;

import com.daema.commgmt.domain.ChargeRegReqReject;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeRegReqRejectDto {

	private long chargeRegReqRejectId;
	private String rejectComment;
	private LocalDateTime rejectDateTime;
	private Long rejectUserId;

	public static ChargeRegReqRejectDto from (ChargeRegReqReject chargeRegReqReject) {
		return ChargeRegReqRejectDto.builder()
				.chargeRegReqRejectId(chargeRegReqReject.getChargeRegReqId())
				.rejectComment(chargeRegReqReject.getRejectComment())
				.rejectDateTime(chargeRegReqReject.getRejectDateTime())
				.rejectUserId(chargeRegReqReject.getRejectUserId())
				.build();
	}
}
















