package com.daema.dto;

import com.daema.domain.ChargeRegReq;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeRegReqDto {

	private long chargeRegReqId;
	private long reqStoreId;
	private int chargeAmt;
	private String chargeName;
	private String category;
	private int telecom;
	private int network;
	private LocalDateTime regiDateTime;
	private int reqStatus;
	private ChargeRegReqRejectDto regReqRejectDto;

	public static ChargeRegReqDto from (ChargeRegReq chargeRegReq) {
		return ChargeRegReqDto.builder()
				.chargeRegReqId(chargeRegReq.getChargeRegReqId())
				.reqStoreId(chargeRegReq.getReqStoreId())
				.chargeName(chargeRegReq.getChargeName())
				.category(chargeRegReq.getCategory())
				.chargeAmt(chargeRegReq.getChargeAmt())
				.telecom(chargeRegReq.getNetworkAttribute().getTelecom())
				.network(chargeRegReq.getNetworkAttribute().getNetwork())
				.regiDateTime(chargeRegReq.getRegiDateTime())
				.reqStatus(chargeRegReq.getReqStatus())
				.regReqRejectDto(ChargeRegReqRejectDto.from(chargeRegReq.getChargeRegReqReject()))
				.build();
	}
}
















