package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.domain.Goods;
import com.daema.core.commgmt.domain.GoodsOption;
import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;
import com.daema.core.commgmt.dto.response.GoodsMatchRespDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomGoodsRepository {
	
	Page<Goods> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin);
	List<Goods> getMatchList(ComMgmtRequestDto requestDto);

	GoodsMatchRespDto goodsMatchBarcode(String commonBarcode);

	GoodsMatchRespDto goodsMatchBarcode(String commonBarcode, Long telecom);


	List<GoodsOption> getColorList(long goodsId, String capacity);

	List<Goods> getGoodsSelectList(Long telecomId);

	List<GoodsOption> getGoodsSelectCapacityList(long goodsId);
}
