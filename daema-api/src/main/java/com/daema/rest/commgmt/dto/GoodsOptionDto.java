package com.daema.rest.commgmt.dto;

import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsOptionDto {

	private long goodsId;
	private long goodsOptionId;
	private String colorName;
	private String distributor;
	private String commonBarcode;

	public static GoodsOptionDto from (GoodsOption goodsOption) {
		return GoodsOptionDto.builder()
				.goodsId(goodsOption.getGoods().getGoodsId())
				.goodsOptionId(goodsOption.getGoodsOptionId())
				.colorName(goodsOption.getColorName())
				.distributor(goodsOption.getDistributor())
				.commonBarcode(goodsOption.getCommonBarcode())
				.build();
	}

	public static GoodsOption toEntity (GoodsOptionDto goodsOptionDto) {
		return GoodsOption.builder()
				.goods(Goods.builder().goodsId(goodsOptionDto.getGoodsId()).build())
				.goodsOptionId(goodsOptionDto.getGoodsOptionId())
				.colorName(goodsOptionDto.getColorName())
				.distributor(goodsOptionDto.getDistributor())
				.commonBarcode(goodsOptionDto.getCommonBarcode())
				.build();
	}
}
