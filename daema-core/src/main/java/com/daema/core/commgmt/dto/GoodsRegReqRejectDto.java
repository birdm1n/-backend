package com.daema.core.commgmt.dto;

import com.daema.core.commgmt.domain.GoodsRegReqReject;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsRegReqRejectDto{

	private long goodsRegReqRejectId;
	private String rejectComment;
	private LocalDateTime rejectDateTime;
	private Long rejectUserId;

	public static GoodsRegReqRejectDto from (GoodsRegReqReject goodsRegReqReject) {
		return GoodsRegReqRejectDto.builder()
				.goodsRegReqRejectId(goodsRegReqReject.getGoodsRegReqId())
				.rejectComment(goodsRegReqReject.getRejectComment())
				.rejectDateTime(goodsRegReqReject.getRejectDateTime())
				.rejectUserId(goodsRegReqReject.getRejectUserId())
				.build();
	}
}
















