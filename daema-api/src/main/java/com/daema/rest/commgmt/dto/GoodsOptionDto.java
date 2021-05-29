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
	private String commonBarcode;
	private String capacity;
	private String unLockYn;
	private String delYn;

	public static GoodsOptionDto from (GoodsOption goodsOption) {
		return GoodsOptionDto.builder()
				.goodsId(goodsOption.getGoods().getGoodsId())
				.goodsOptionId(goodsOption.getGoodsOptionId())
				.colorName(goodsOption.getColorName())
				.commonBarcode(goodsOption.getCommonBarcode())
				.capacity(goodsOption.getCapacity())
				.delYn(goodsOption.getDelYn())
				.unLockYn(goodsOption.getUnLockYn())
			.build();
	}

	public static GoodsOption toEntity (GoodsOptionDto goodsOptionDto) {
		return GoodsOption.builder()
				.goods(Goods.builder().goodsId(goodsOptionDto.getGoodsId()).build())
				.goodsOptionId(goodsOptionDto.getGoodsOptionId())
				.colorName(goodsOptionDto.getColorName())
				.commonBarcode(goodsOptionDto.getCommonBarcode())
				.capacity(goodsOptionDto.getCapacity())
				.delYn(goodsOptionDto.getDelYn())
				.unLockYn(goodsOptionDto.getUnLockYn())
			.build();
	}
}
