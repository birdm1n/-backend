package com.daema.dto;

import com.daema.domain.Charge;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeMgmtDto {

	private long chargeId;
	private String chargeName;
	private int chargeAmt;
	private String category;
	private int telecom;
	private int network;
	private String originKey;
	private String useYn;
	private String matchingYn;
	private String delYn;
	private LocalDateTime regiDateTime;
	private String voiceAmt;
	private String dataAmt;
	private String smsAmt;
	private Integer discountAmt;

	//TODO 관리자 구분 불가하여 임시 사용. 신규 등록과 요청 구분용
	private String reqYn;

	public static ChargeMgmtDto from (Charge charge) {
		return ChargeMgmtDto.builder()
				.chargeId(charge.getChargeId())
				.chargeName(charge.getChargeName())
				.category(charge.getCategory())
				.chargeAmt(charge.getChargeAmt())
				.telecom(charge.getNetworkAttribute().getTelecom())
				.network(charge.getNetworkAttribute().getNetwork())
				.originKey(charge.getOriginKey())
				.regiDateTime(charge.getRegiDateTime())
				.useYn(charge.getUseYn())
				.matchingYn(charge.getMatchingYn())
				.delYn(charge.getDelYn())
				.voiceAmt(charge.getVoiceAmt())
				.dataAmt(charge.getDataAmt())
				.smsAmt(charge.getSmsAmt())
				.discountAmt(charge.getDiscountAmt())
				.build();
	}
}
