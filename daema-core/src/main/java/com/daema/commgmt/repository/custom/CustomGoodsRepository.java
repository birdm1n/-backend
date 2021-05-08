package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.daema.commgmt.domain.dto.response.GoodsMatchRespDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomGoodsRepository {
	
	Page<Goods> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin);
	List<Goods> getMatchList(ComMgmtRequestDto requestDto);

	GoodsMatchRespDto goodsMatchBarcode(String commonBarcode);

    List<Goods> getCapacityList(Long storeId);

	List<GoodsOption> getColorList(long goodsId);
}
