package com.daema.wms.domain.dto.response;

import com.daema.base.util.CommonUtil;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpeningCurrentResponseDto {
    // 개통 ID
    private Long openingId;

    //통신사
    private String telecomName;

    // 기기 ID
    private Long dvcId;

    //입고일
    private LocalDateTime inStockRegiDate;

    //입고 경과일
    private Long diffInStockRegiDate;

    public Long getDiffInStockRegiDate() {
        return CommonUtil.diffDaysLocalDateTime(this.inStockRegiDate);
    }

    //개통일
    private LocalDate openingDate;

    //개통 경과일
    private Long diffOpeningDate;

    public Long getDiffOpeningDate() {
        String localDateToString = this.openingDate.toString();
        localDateToString = localDateToString.concat(" 00:00:00");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime openingDateTime = LocalDateTime.parse(localDateToString, formatter);
        return CommonUtil.diffDaysLocalDateTime(openingDateTime);
    }

    // 이전보유처
    private String prevStockName;

    // 현재보유처
    private String nextStockName;

    //재고구분
    private WmsEnum.StockType stockType;
    private String stockTypeMsg;

    public String getStockTypeMsg() {
        return this.stockType != null ? this.stockType.getStatusMsg(): "";
    }

    //제조사
    private String makerName;

    //기기명
    private String goodsName;

    //모델명
    private String modelName;

    //용량
    private String capacity;

    //색상
    private String colorName;

    // 기기일련번호(바코드)
    private String rawBarcode;

    //입고단가
    private int inStockAmt;

    // 고객 이름
    private String cusName;

    // 고객 연락처 1
    private String cusPhone1;

    // 고객 연락처 2
    private String cusPhone2;

    // 고객 연락처 3
    private String cusPhone3;

    // 고객 배송 주소
    private String addr1;

    // 고객 배송 주소 상세
    private String addr2;

    // 우편 코드
    private String zipCode;

    // 철회 가능 여부
    private WmsEnum.OpeningStatus cancelStatus;
    private String cancelStatusMsg;

    public String getCancelStatusMsg() {
        return this.cancelStatus != null ? this.cancelStatus.getCancelMsg() : "";
    }

    // 철회일자
    private LocalDate cancelDate;

    // 철회메모
    private String cancelMemo;

}
