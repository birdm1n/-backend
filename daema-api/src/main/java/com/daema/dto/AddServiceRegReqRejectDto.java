package com.daema.dto;

import com.daema.domain.AddServiceRegReqReject;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddServiceRegReqRejectDto {

	private long addSvcRegReqId;
	private String rejectComment;
	private LocalDateTime rejectDateTime;
	private Long rejectUserId;

	public static AddServiceRegReqRejectDto from (AddServiceRegReqReject addServiceRegReqReject) {
		return AddServiceRegReqRejectDto.builder()
				.addSvcRegReqId(addServiceRegReqReject.getAddSvcRegReqId())
				.rejectComment(addServiceRegReqReject.getRejectComment())
				.rejectDateTime(addServiceRegReqReject.getRejectDateTime())
				.rejectUserId(addServiceRegReqReject.getRejectUserId())
				.build();
	}
}
















