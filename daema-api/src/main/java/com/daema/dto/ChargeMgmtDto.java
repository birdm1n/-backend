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
				.build();
	}
}
