package com.daema.wms.domain.dto.request;




import com.daema.base.dto.SearchParamDto;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class MoveMgmtRequestDto extends SearchParamDto { //이동현황


    // 통신사
    private Long telecom;

//    //입고일
    private String inStockRegiDate;

    //출고일 (이동, 이관일)
    private String moveRegiDate;

    // 보유처
    private Long stockId;

    // 재고구분
    private WmsEnum.StockType stockType;

    //제조사
    private Long maker;

    //기기명(모델명)
    private Long goodsId;

    //용량
    private String capacity;

    //색상
    private String colorName;

    //입고상태
    private WmsEnum.InStockStatus inStockStatus;

    // 제품상태 =  N, "-" / Y, "불량"
    private String productFaultyYn;

    //배송방법
    private WmsEnum.DeliveryType deliveryType;

    // 배송상태
    private WmsEnum.DeliveryStatus deliveryStatus;

    // 외장상태 = "상" / "중" / "하" / "파손"
    private WmsEnum.DeviceExtrrStatus extrrStatus;

    // 기기일련번호(바코드)
    private String barcode;


    private Long dvcId;

    // 반품비
    private int returnStockAmt;
    // 추가 차감비
    private int addDdctAmt;
    // 출고가 차감 YN
    private String ddctReleaseAmtYn;
    // 누락제품
    private String missProduct;

}
