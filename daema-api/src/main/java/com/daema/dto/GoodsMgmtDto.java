package com.daema.dto;

import com.daema.domain.Goods;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsMgmtDto {

	private long goodsId;
	private String goodsName;
	private String modelName;
	private int maker;
	private int telecom;
	private int network;
	private String capacity;
	private String originKey;
	private String useYn;
	private String matchingYn;
	private String delYn;
	private LocalDateTime regiDateTime;
	private List<GoodsOptionDto> optionList;

	//TODO 관리자 구분 불가하여 임시 사용. 신규 등록과 요청 구분용
	private String reqYn;

	public static GoodsMgmtDto from (Goods goods) {
		return GoodsMgmtDto.builder()
				.goodsId(goods.getGoodsId())
				.goodsName(goods.getGoodsName())
				.modelName(goods.getModelName())
				.maker(goods.getMaker())
				.telecom(goods.getNetworkAttribute().getTelecom())
				.network(goods.getNetworkAttribute().getNetwork())
				.capacity(goods.getCapacity())
				.originKey(goods.getOriginKey())
				.regiDateTime(goods.getRegiDateTime())
				.useYn(goods.getUseYn())
				.matchingYn(goods.getMatchingYn())
				.delYn(goods.getDelYn())
				.optionList(Optional.ofNullable(goods.getOptionList())
						.orElseGet(Collections::emptyList)
						.stream()
						.map(goodsOption -> GoodsOptionDto.from(goodsOption))
						.collect(Collectors.toList()))
				.build();
	}
}
