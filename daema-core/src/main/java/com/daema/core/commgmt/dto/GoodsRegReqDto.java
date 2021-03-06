package com.daema.core.commgmt.dto;

import com.daema.core.commgmt.domain.GoodsRegReq;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsRegReqDto {

	private long goodsRegReqId;
	private long reqStoreId;
	private String goodsName;
	private String modelName;
	private Long maker;
	private Long telecom;
	private Long network;
	private String capacity;
	private LocalDateTime regiDateTime;
	private String reqStatus;
	private GoodsRegReqRejectDto regReqRejectDto;
	private String makerName;
	private String networkName;
	private String telecomName;
	private String reqStoreName;

	public static GoodsRegReqDto from (GoodsRegReq goodsRegReq) {
		return GoodsRegReqDto.builder()
				.goodsRegReqId(goodsRegReq.getGoodsRegReqId())
				.reqStoreId(goodsRegReq.getReqStoreId())
				.goodsName(goodsRegReq.getGoodsName())
				.modelName(goodsRegReq.getModelName())
				.maker(goodsRegReq.getMaker())
				.telecom(goodsRegReq.getNetworkAttribute().getTelecom())
				.network(goodsRegReq.getNetworkAttribute().getNetwork())
				.regiDateTime(goodsRegReq.getRegiDateTime())
				.reqStatus(goodsRegReq.getReqStatus())
				.regReqRejectDto(GoodsRegReqRejectDto.from(goodsRegReq.getGoodsRegReqReject()))
				.makerName(goodsRegReq.getMakerName())
				.telecomName(goodsRegReq.getTelecomName())
				.networkName(goodsRegReq.getNetworkName())
				.reqStoreName(goodsRegReq.getReqStoreName())
			.build();
	}
}
















