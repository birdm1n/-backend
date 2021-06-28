package com.daema.core.wms.dto.request;

import com.daema.core.base.dto.SearchParamDto;
import com.daema.core.wms.domain.enums.WmsEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpeningCurrentRequestDto extends SearchParamDto {

    //통신사(id)
    private Long telecom;

    //입고일
    private String inStockRegiDate;

    //개통일
    private String openingDate;

    // 보유처(id)
    private Long stockId;

    // 재고구분
    private WmsEnum.StockType stockType;

    //제조사(id)
    private Long maker;

    //기기명(id)
    private Long goodsId;

    //용량
    private String capacity;

    // 철회상태
    private WmsEnum.OpeningStatus openingStatus;

    //색상
    private String colorName;

    // 고객 이름
    private String cusName;

    // 고객 연락처
    private String cusPhone;

    // 기기일련번호(바코드)
    private String barcode;

}
