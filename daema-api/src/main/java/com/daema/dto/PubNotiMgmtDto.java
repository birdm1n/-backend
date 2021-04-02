package com.daema.dto;

import com.daema.domain.PubNoti;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PubNotiMgmtDto {

	private long pubNotiId;
	private int supportAmt;
	private int releaseAmt;
	private LocalDate releaseDate;
	private long regiUserId;
	private LocalDateTime regiDateTime;
	private Long delUserId;
	private String delYn;
	private LocalDateTime delDateTime;
	private long chargeId;
	private long goodsId;

	public static PubNotiMgmtDto from (PubNoti pubNoti) {
		return PubNotiMgmtDto.builder()
				.pubNotiId(pubNoti.getPubNotiId())
				.supportAmt(pubNoti.getSupportAmt())
				.releaseAmt(pubNoti.getReleaseAmt())
				.releaseDate(pubNoti.getReleaseDate())
				.regiUserId(pubNoti.getRegiUserId())
				.regiDateTime(pubNoti.getRegiDateTime())
				.delUserId(pubNoti.getDelUserId())
				.delYn(pubNoti.getDelYn())
				.delDateTime(pubNoti.getDelDateTime())
				.chargeId(pubNoti.getChargeId())
				.goodsId(pubNoti.getGoodsId())
				.build();
	}
}
