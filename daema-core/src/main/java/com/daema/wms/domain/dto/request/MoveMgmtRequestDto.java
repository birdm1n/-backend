package com.daema.wms.domain.dto.request;




import com.daema.base.dto.SearchParamDto;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class MoveMgmtRequestDto extends SearchParamDto { //이동현황


    // 통신사
    private Integer telecom;

//    //입고일
    private String inStockRegiDate;

    //이동일
    private String moveRegiDate;


    // 이전 보유처
    private Long prevStockId;
    private String prevStockName;

    // 현재 보유처
    private Long nextStockId;
    private String nextStockName;

    // 재고구분
    private WmsEnum.MoveStockType moveStockType;
    private String moveStockTypeMsg;

    //제조사
    private Integer maker;

    //기기명(모델명)
    private Long goodsId;
    private String goodsName;
    private String modelName;

    //용량
    private String capacity;

    //색상
    private String colorName;

    //입고상태
    private WmsEnum.InStockStatus inStockStatus;
    private String inStockStatusMsg;

    // 제품상태 =  N, "-" / Y, "불량"
    private String productFaultyYn;

    //배송방법
    private WmsEnum.DeliveryType deliveryType;
    private String deliveryTypeMsg;

    // 배송상태
    private WmsEnum.DeliveryStatus deliveryStatus;
    private String deliveryStatusMsg;

    // 외장상태 = "상" / "중" / "하" / "파손"
    private WmsEnum.DeviceExtrrStatus extrrStatus;
    private String extrrStatusMsg;

    // 기기일련번호(바코드)
    private String fullBarcode;


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
