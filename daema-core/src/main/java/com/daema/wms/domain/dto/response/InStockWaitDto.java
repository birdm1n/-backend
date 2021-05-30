package com.daema.wms.domain.dto.response;

import com.daema.wms.domain.InStockWait;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InStockWaitDto {

    private Long waitId;

    // 차감비
    private int ddctAmt;

    // 추가 차감비
    private int addDdctAmt;

    // 출고가 차감 여부
    private String ddctReleaseAmtYn;

    // 누락제품
    private String missProduct;

    // 입고메모
    private String inStockMemo;

    // 소유권을 가지는 Store = 관리점
    private Long ownStoreId;

    // 보유처 StoreId
    private Long holdStoreId;

    /**
     Desc : 기기별 입력정보 및 모델별 입력정보
     */
    // 통신사
    private int telecom;
    private String telecomName;

    // 공급처
    private Long provId;

    // 보유처
    private Long stockId;
    private String stockName;

    // 재고구분
    private WmsEnum.StockStatStr statusStr;
    private String statusStrMsg;

    //제조사
    private int maker;
    private String makerName;


    // 상품명_모델명
    private long goodsId;
    private String goodsName;
    private String modelName;

    // 용량
    private String capacity;

    // 색상_기기일련번호(바코드)
    private long goodsOptionId;
    private String colorName;
    private String commonBarcode;
    private String fullBarcode;
    private String rawBarcode;
    private String serialNo;

    // 입고단가
    private int inStockAmt;


    // 입고상태
    private WmsEnum.InStockStatus inStockStatus;
    private String inStockStatusMsg;

    // 제품상태 =  N, "-" / Y, "불량"
    private String productFaultyYn;

    // 구성품 누락여부 = Y / N
    private String productMissYn;

    // 외장상태 = "상" / "중" / "하" / "파손"
    private WmsEnum.DeviceExtrrStatus extrrStatus;
    private String extrrStatusMsg;

    private LocalDateTime regiDateTime;
    private Long regiUserId;
    private String regiUserName;
    private LocalDateTime updDateTime;
    private Long updUserId;
    private String updUserName;

    public static InStockWaitDto from(InStockWait entity) {
        return InStockWaitDto.builder()
                .waitId(entity.getWaitId())
                .ddctAmt(entity.getDdctAmt())
                .addDdctAmt(entity.getAddDdctAmt())
                .ddctReleaseAmtYn(entity.getDdctReleaseAmtYn())
                .missProduct(entity.getMissProduct())
                .inStockMemo(entity.getInStockMemo())
                .ownStoreId(entity.getOwnStoreId())
                .holdStoreId(entity.getHoldStoreId())
                .telecom(entity.getTelecom())
                .telecomName(entity.getTelecomName())
                .provId(entity.getProvId())
                .stockId(entity.getStockId())
                .stockName(entity.getStockName())
                .statusStr(entity.getStatusStr())
                .statusStrMsg(
                        entity.getStatusStr().getStatusMsg() != null ? entity.getStatusStr().getStatusMsg():""
                )
                .maker(entity.getMaker())
                .makerName(entity.getMakerName())
                .goodsId(entity.getGoodsId())
                .goodsName(entity.getGoodsName())
                .modelName(entity.getModelName())
                .capacity(entity.getCapacity())
                .goodsOptionId(entity.getGoodsOptionId())
                .colorName(entity.getColorName())
                .commonBarcode(entity.getCommonBarcode())
                .fullBarcode(entity.getFullBarcode())
                .rawBarcode(entity.getRawBarcode())
                .serialNo(entity.getSerialNo())
                .inStockAmt(entity.getInStockAmt())
                .inStockStatus(entity.getInStockStatus())
                .inStockStatusMsg(entity.getInStockStatus().getStatusMsg())
                .productFaultyYn(entity.getProductFaultyYn())
                .productMissYn(entity.getProductMissYn())
                .extrrStatus(entity.getExtrrStatus())
                .extrrStatusMsg(
                        entity.getExtrrStatus() != null ? entity.getExtrrStatus().getStatusMsg() : ""
                )
                .regiDateTime(entity.getRegiDateTime())
                .regiUserId(entity.getRegiUserId().getSeq())
                .regiUserName(entity.getRegiUserId().getUsername())
                .updDateTime(entity.getUpdDateTime())
                .updUserId(entity.getUpdUserId().getSeq())
                .updUserName(entity.getUpdUserId().getUsername())
                .build();
    }

}